package jbehave.plugin.eclipse;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class VerificationLaunchShortcut implements ILaunchShortcut {
	
	/**
	 * @see ILaunchShortcut#launch(ISelection, String)
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			searchAndLaunch(((IStructuredSelection)selection).toArray(), mode);
		} 
	}

	private void searchAndLaunch(Object[] selections, String mode) {
		try {
			if (selections != null) {
				if (selections.length == 0) {
					MessageDialog.openInformation(JBehavePlugin.getActiveWorkbenchShell(),
							"Launching Behavior Verification",
							"No behavior found");
					return;
				}
				launchType(selections[0], mode);
			}
		} catch (LaunchCancelledByUserException e) {
			// OK, silently move on
		}
	}
	
	private void launchType(Object selection, String mode) throws LaunchCancelledByUserException {
		ConfigurationState state= null;
		try {
			state = new BehaviorSearchEngine().findBehaviors(selection); 
		} catch (InterruptedException e) {
			JBehavePlugin.log(e);
			return;
		} catch (InvocationTargetException e) {
			JBehavePlugin.log(e);
			return;
		}
		if (state == null) {
			MessageDialog.openInformation(
					JBehavePlugin.getActiveWorkbenchShell(), 
					"Launch Behavior Verification", 
					"No behavior found"); 
		} else {
			launch(state, mode);
		}
	}
	
	private void launch(ConfigurationState state, String mode) throws LaunchCancelledByUserException {
		ILaunchConfiguration config = findLaunchConfiguration(
			mode, 
			state, 
			"",  //$NON-NLS-1$
			"" //$NON-NLS-1$
		);
		if (config == null) {
			config = createConfiguration(state);
		}
		launchConfiguration(mode, config);
	}
	
	private ILaunchConfiguration findLaunchConfiguration(String mode, ConfigurationState state, String container, String name) throws LaunchCancelledByUserException {
		ILaunchConfigurationType configType= getJBehaveLaunchConfigType();
		List<ILaunchConfiguration> candidateConfigs = Collections.emptyList();
		try {
			ILaunchConfiguration[] configs= DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);
			candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
			for (int i= 0; i < configs.length; i++) {
				if (new ConfigurationState(configs[i]).equals(state)) {
					candidateConfigs.add(configs[i]);
				}
			}
		} catch (CoreException e) {
			JBehavePlugin.log(e);
		}
		
		// If there are no existing configs associated with the IType, create one.
		// If there is exactly one config associated with the IType, return it.
		// Otherwise, if there is more than one config associated with the IType, prompt the
		// user to choose one.
		int candidateCount= candidateConfigs.size();
		if (candidateCount < 1) {
			return null;
		} else if (candidateCount == 1) {
			return (ILaunchConfiguration) candidateConfigs.get(0);
		} else {
			// Prompt the user to choose a config.  A null result means the user
			// cancelled the dialog, in which case this method returns null,
			// since cancelling the dialog should also cancel launching anything.
			ILaunchConfiguration config= chooseConfiguration(candidateConfigs, mode);
			if (config != null) {
				return config;
			}
		}
		return null;
	}

	/**
	 * Show a selection dialog that allows the user to choose one of the specified
	 * launch configurations.  Return the chosen config, or <code>null</code> if the
	 * user cancelled the dialog.
	 * @param configList 
	 * @param mode 
	 * @return ILaunchConfiguration
	 * @throws LaunchCancelledByUserException 
	 */
	protected ILaunchConfiguration chooseConfiguration(List configList, String mode) throws LaunchCancelledByUserException {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(JBehavePlugin.getActiveWorkbenchShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle("Select JBehave Configuration"); 
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			dialog.setMessage("Select JBehave debug configuration"); 
		} else {
			dialog.setMessage("Select JBehave run configuration"); 
		}
		dialog.setMultipleSelection(false);
		int result= dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration)dialog.getFirstResult();
		}
		throw new LaunchCancelledByUserException();
	}
	
	/**
	 * Returns the local java launch config type
	 * @return ILaunchConfigurationType
	 */
	private ILaunchConfigurationType getJBehaveLaunchConfigType() {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(
				JBehaveLaunchConfiguration.ID_JBEHAVE_APPLICATION);		
	}

//	private void launchContainer(IJavaElement container, String mode) throws LaunchCancelledByUserException {
//		String handleIdentifier= container.getHandleIdentifier();
//		ILaunchConfiguration config = findLaunchConfiguration(
//			mode, 
//			container, 
//			handleIdentifier, 
//			"",  //$NON-NLS-1$
//			"" //$NON-NLS-1$
//		);
//		String name= JavaElementLabels.getTextLabel(container, JavaElementLabels.ALL_FULLY_QUALIFIED);
//		if (config == null) {
//			config = createConfiguration(
//				container.getJavaProject(),
//				name,
//				"", //$NON-NLS-1$
//				handleIdentifier,
//				"" //$NON-NLS-1$
//			);
//		}
//		launchConfiguration(mode, config);
//	}

	public class LaunchCancelledByUserException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * @see ILaunchShortcut#launch(IEditorPart, String)
	 */
	public void launch(IEditorPart editor, String mode) {
		IJavaElement element= null;
		IEditorInput input = editor.getEditorInput();
		element = (IJavaElement) input.getAdapter(IJavaElement.class);

		if (element != null) {
			searchAndLaunch(new Object[] {element}, mode);
		} 
	}


	protected void launchConfiguration(String mode, ILaunchConfiguration config) {
		if (config != null) {
			DebugUITools.launch(config, mode);
		}
	}
	

	private ILaunchConfiguration createConfiguration(ConfigurationState state) {
		ILaunchConfiguration config= null;
		try {
			ILaunchConfigurationType configType= getJBehaveLaunchConfigType();
			ILaunchConfigurationWorkingCopy wc = configType.newInstance(
					null, DebugPlugin.getDefault().getLaunchManager().generateUniqueLaunchConfigurationNameFrom(state.getName())); 
			state.setAttributes(wc);
			config= wc.doSave();		
		} catch (CoreException ce) {
			JBehavePlugin.log(ce);
		}
		return config;
	}

}

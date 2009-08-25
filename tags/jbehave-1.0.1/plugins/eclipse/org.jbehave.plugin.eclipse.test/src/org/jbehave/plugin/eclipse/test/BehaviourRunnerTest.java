package org.jbehave.plugin.eclipse.test;

import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.TextConsole;
import org.jbehave.plugin.eclipse.ConfigurationState;
import org.jbehave.plugin.eclipse.JBehaveLaunchConfiguration;



public class BehaviourRunnerTest extends TestCase {
	private TestProject testProject;
	
	protected void setUp() throws Exception {
		super.setUp();
		testProject=new TestProject();
		
	}
	
	public void testBuggyClassGetsProblemMarker() throws CoreException, OperationCanceledException, InterruptedException{
		IWorkspaceRunnable runnable=new IWorkspaceRunnable(){
			public void run(IProgressMonitor monitor) throws CoreException {
				IPackageFragment pack = testProject.createPackage("paket1") ;
				IType type= testProject.createType(pack, "AClass.java", "public class AClass {public voi m(){}}");
			}
		};
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.run(runnable,null);
		Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		IMarker[] markers = workspace.getRoot().findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		assertEquals(1, markers.length);
	}   

	public void testCorrectClassHasNoProblemMarkers() throws CoreException, OperationCanceledException, InterruptedException{
		IWorkspaceRunnable runnable=new IWorkspaceRunnable(){		
			public void run(IProgressMonitor monitor) throws CoreException {
				IPackageFragment pack = testProject.createPackage("paket1") ;
				IType type= testProject.createType(pack, "AClass.java", "public class AClass {public void m(){}}");	
			}
		};
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.run(runnable,null);
		Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		IMarker[] markers = workspace.getRoot().findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		assertEquals(0, markers.length);
	}

	
	public void testUsingJBehaveJar() throws CoreException, OperationCanceledException, InterruptedException, MalformedURLException, IOException{
		testProject.addJar("org.jbehave", "lib/jbehave.jar");
		IWorkspaceRunnable runnable=new IWorkspaceRunnable(){		
			public void run(IProgressMonitor monitor) throws CoreException {
				
				IPackageFragment pack = testProject.createPackage("paket1") ;
				IType type= testProject.createType(pack, "AClass.java", "public class AClass {public void m(){}}");	
				IType testClass = testProject.createType(pack, "AClassBehaviour.java", "public class AClassBehaviour extends org.jbehave.core.mock.UsingMatchers{public void shouldAddUp(){ensureThat(2,eq(1+1));}}");
			
			}
		};
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.run(runnable,null);
		Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		IMarker[] markers = workspace.getRoot().findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		assertEquals(0, markers.length);
	}

	public void testLaunchingTestCase() throws CoreException, OperationCanceledException, InterruptedException, MalformedURLException, IOException{
		testProject.addJar("org.jbehave", "lib/jbehave.jar");
		IWorkspaceRunnable runnable=new IWorkspaceRunnable(){		
			public void run(IProgressMonitor monitor) throws CoreException {
				IPackageFragment pack = testProject.createPackage("paket1") ;
				IType type= testProject.createType(pack, "AClass.java", "public class AClass {public void m(){}}");	
				IType testClass = testProject.createType(pack, "AClassBehaviour.java", "public class AClassBehaviour extends org.jbehave.core.mock.UsingMatchers{public void shouldAddUp(){ensureThat(2,eq(1+1));}}");
			}
		};
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.run(runnable,null);
		Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		IMarker[] markers = workspace.getRoot().findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		assertEquals(0, markers.length);
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType configType = lm.getLaunchConfigurationType(JBehaveLaunchConfiguration.ID_JBEHAVE_APPLICATION);	
	
		ConfigurationState state=new ConfigurationState();
		state.setProjectName(testProject.getProject().getName());
		state.setBehaviorClass("paket1.AClassBehaviour");
		state.setBehaviorMethod("shouldAddUp");
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(
				null, lm.generateUniqueLaunchConfigurationNameFrom(state.getName())); 

		state.setAttributes(wc);
		ILaunchConfiguration config = wc.doSave();
	
		IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		final TextConsole[] console=new TextConsole[1];				
		manager.addConsoleListener(new IConsoleListener(){
			public void consolesAdded(IConsole[] consoles) {
				 console[0]=((TextConsole)consoles[0]);
				 console[0].activate();
			}
			public void consolesRemoved(IConsole[] consoles) {
			}
			
		});

		DebugUIPlugin.launchInForeground(config, ILaunchManager.RUN_MODE);		
		waitForJobs();
		delay(1000);
		
		IDocument document = console[0].getDocument();
		String output=document.get();
		
		assertTrue(output.startsWith(".\r\nTime:"));
		
	}

	private void delay(long waitTimeInMillis){
		Display display= Display.getCurrent();
		if (display!=null){
			long endTimeMillis=System.currentTimeMillis()+waitTimeInMillis;
			while (System.currentTimeMillis()<endTimeMillis){
				if (!display.readAndDispatch()){
					display.sleep();
				}
			}
			display.update();
		} else {
			try {
				Thread.sleep(waitTimeInMillis);
			} catch (InterruptedException e) {}
		}
			
	}
	
	public void waitForJobs(){
		while(Platform.getJobManager().currentJob() != null)
			delay(1000);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		testProject.dispose();
	}
	
	
	
	

}

package jbehave.plugin.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class ConfigurationState {

	private String projectName = "";
	private String behaviorClassName = "";
	private String behaviorMethodName = "";

	public ConfigurationState() {
	}

	public ConfigurationState(IType type) {
		projectName = type.getJavaProject().getElementName();
		behaviorClassName = type.getFullyQualifiedName('.');
	}

	public ConfigurationState(ILaunchConfiguration config) {
		try {
			this.projectName = config.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			this.behaviorClassName = config.getAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOR_CLASS, "");
			this.behaviorMethodName = config.getAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOR_METHOD, "");
		} catch (CoreException e) {
		}
	}

	public void setProjectName(String name) {
		this.projectName = name;
	}

	public void setBehaviorClass(String className) {
		this.behaviorClassName = className;
	}
	
	public void setBehaviorMethod(String methodName) {
		this.behaviorMethodName = methodName;
	}

	public void setAttributes(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "jbehave.core.Run");
		config.setAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOR_CLASS, behaviorClassName);
		config.setAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOR_METHOD, behaviorMethodName);
	}
	
	public boolean equals(Object object) {
		boolean result = false;
		if (object instanceof ConfigurationState) {
			ConfigurationState that = (ConfigurationState) object;
			result = projectName.equals(that.projectName) &&
					behaviorClassName.equals(that.behaviorClassName) &&
					behaviorMethodName.equals(that.behaviorMethodName);
		}
		return result;
	}

	public String getName() {
		String name = behaviorMethodName;
		if (name.length() == 0) {
			name = shortClassName(behaviorClassName);
		}
		return name;
	}

	private String shortClassName(String behaviorClassName) {
		String name = behaviorClassName;
		int index = behaviorClassName.lastIndexOf('.');
		if (index > -1) {
			name = behaviorClassName.substring(index + 1);
		}
		return name;
	}

}

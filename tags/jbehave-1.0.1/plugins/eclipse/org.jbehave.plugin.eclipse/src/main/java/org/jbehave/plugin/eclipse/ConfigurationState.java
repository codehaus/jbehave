package org.jbehave.plugin.eclipse;

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
			this.behaviorClassName = config.getAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOUR_CLASS, "");
			this.behaviorMethodName = config.getAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOUR_METHOD, "");
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
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "org.jbehave.core.Run");
		config.setAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOUR_CLASS, behaviorClassName);
		config.setAttribute(JBehaveLaunchConfiguration.ATTR_BEHAVIOUR_METHOD, behaviorMethodName);
	}
	
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((behaviorClassName == null) ? 0 : behaviorClassName.hashCode());
		result = PRIME * result + ((behaviorMethodName == null) ? 0 : behaviorMethodName.hashCode());
		result = PRIME * result + ((projectName == null) ? 0 : projectName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigurationState other = (ConfigurationState) obj;
		if (behaviorClassName == null) {
			if (other.behaviorClassName != null)
				return false;
		} else if (!behaviorClassName.equals(other.behaviorClassName))
			return false;
		if (behaviorMethodName == null) {
			if (other.behaviorMethodName != null)
				return false;
		} else if (!behaviorMethodName.equals(other.behaviorMethodName))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
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

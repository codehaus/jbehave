package org.jbehave.plugin.idea.selena;

import com.intellij.execution.junit.TestSearchScope;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;

public class ConfigurationData
{
  private String packageName = "";
  private String behaviorClass = "";
  private String behaviourMethod = "";
  private String moduleName = "";
  private Module module;
  private String vmParameters;
  private TestSearchScope.Wrapper searchScope = new TestSearchScope.Wrapper();
  private String workingPath;

  public void setPackageName(String name) {
    this.packageName = name;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setBehaviorClass(String name) {
    this.behaviorClass = name;
  }

  public String getBehaviorClass() {
    return behaviorClass;
  }

  public String getBehaviourMethod() {
    return behaviourMethod;
  }

  public void setBehaviorMethod(String behaviourMethod) {
    this.behaviourMethod = behaviourMethod;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    if (!this.moduleName.equals(moduleName)) {
      this.moduleName = moduleName;
      this.module = null;
    }
  }

  public Module getModule(Project project) {
    if (module == null) {
      module = lookup(getModules(project), moduleName);
    }
    return module;
  }

  public Module[] getModules(Project project) {
    return ModuleManager.getInstance(project).getModules();
  }

  private Module lookup(Module[] modules, String moduleName) {
    for (Module module : modules) {
      if (module.getName().equals(moduleName)) {
        return module;
      }
    }
    return modules.length > 0 ? modules[0] : null;
  }

  public ConfigurationData copy() {
    ConfigurationData data = new ConfigurationData();
    data.setBehaviorClass(getBehaviorClass());
    data.setBehaviorMethod(getBehaviourMethod());
    data.setModuleName(getModuleName());
    data.setPackageName(getPackageName());
    data.setVmParameters(getVmParameters());
    return data;
  }

  public TestSearchScope getScope() {
    return searchScope.getScope();
  }

  public void setScope(TestSearchScope testseachscope) {
    searchScope.setScope(testseachscope);
  }

  public boolean isGeneratedName(String name) {
    return name.equals(suggestName());
  }

  public String suggestName() {
    return isMethodSpecified() ? behaviourMethod : shortName();
  }

  private String shortName() {
    int index = behaviorClass.lastIndexOf('.');
    return index == -1 ? behaviorClass : behaviorClass.substring(index + 1);
  }

  public void setVmParameters(String value) {
    vmParameters = value;
  }

  public String getVmParameters() {
    return vmParameters;
  }

  public boolean isMethodSpecified() {
    return behaviourMethod.length() != 0;
  }

  public void setWorkingPath(String workingPath) {
    this.workingPath = workingPath;
  }

  public String getWorkingPath() {
    return workingPath;
  }
}

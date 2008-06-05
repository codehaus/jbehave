package org.jbehave.plugin.idea.selena;

import com.intellij.execution.ExecutionUtil;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class JBehaveConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable
{
  private PsiClass behaviorClass;
  private JBehaveConfigurationType configurationType;

  public JBehaveConfigurationProducer(PsiClass behaviorClass, JBehaveConfigurationType configurationType) {
    super(configurationType);
    this.behaviorClass = behaviorClass;
    this.configurationType = configurationType;
  }

  public PsiElement getSourceElement() {
    return behaviorClass;
  }

  protected RunnerAndConfigurationSettingsImpl createConfigurationByElement(Location location, ConfigurationContext configurationContext) {
    location = ExecutionUtil.stepIntoSingleClass(location);
    PsiClass aClass = configurationType.getBehaviorClass(location.getPsiElement());
    if (aClass == null) return null;
    PsiMethod currentMethod = configurationType.getBehaviourMethodElement(location.getPsiElement());
    RunnerAndConfigurationSettingsImpl settings = cloneTemplateConfiguration(location.getProject(), configurationContext);
    JBehaveConfiguration configuration = (JBehaveConfiguration) settings.getConfiguration();
    ConfigurationData data = configuration.getData();
    data.setBehaviorClass(aClass.getQualifiedName());
    if (currentMethod != null) {
      data.setBehaviorMethod(currentMethod.getName());
    }
    configuration.setModule(ExecutionUtil.findModule(aClass));
    configuration.setName(data.suggestName());
    return settings;
  }

  public int compareTo(Object o) {
    return PREFERED;
  }
}
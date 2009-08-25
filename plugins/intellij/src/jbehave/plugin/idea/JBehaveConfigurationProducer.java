package jbehave.plugin.idea;

import com.intellij.execution.ExecutionUtil;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class JBehaveConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
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
        JBehaveRunConfiguration configuration = (JBehaveRunConfiguration) settings.getConfiguration();
        configuration.setBehaviorClass(ClassUtil.fullName(aClass));
        if (currentMethod != null) {
            configuration.setBehaviorMethod(currentMethod.getName());
        }
        configuration.setModule(ExecutionUtil.findModule(aClass));
        configuration.setName(createName(aClass, currentMethod));
        return settings;
    }

    private String createName(PsiClass aClass, PsiMethod currentMethod) {
        return currentMethod == null ? ClassUtil.shortName(aClass) : currentMethod.getName();
    }

    public int compareTo(Object o) {
        return -1;
    }
}

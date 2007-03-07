package org.jbehave.plugin.idea;

import com.intellij.execution.LocatableConfigurationType;
import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

public class JBehaveConfigurationType implements LocatableConfigurationType {
    private final ConfigurationFactory factory;

    public JBehaveConfigurationType() {
        this.factory = new ConfigurationFactory(this) {

            public RunConfiguration createTemplateConfiguration(Project project) {
                return new JBehaveRunConfiguration(project, this, "");
            }
        };
    }

    public String getDisplayName() {
        return "JBehave";
    }

    public String getConfigurationTypeDescription() {
        return "JBehave Configuration";
    }

    public Icon getIcon() {
        return Icons.LOGO;
    }

    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{factory};
    }

    @NonNls
    public String getComponentName() {
        return "#org.jbehave.plugin.idea.JBehaveConfigurationType";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public boolean isConfigurationByElement(RunConfiguration configuration, Project project, PsiElement element) {
        PsiClass behaviorClass = getBehaviorClass(element);
        if (behaviorClass == null)
            return false;
        JBehaveRunConfiguration runConfiguration = ((JBehaveRunConfiguration) configuration);
        return Comparing.equal(ClassUtil.fullName(behaviorClass), runConfiguration.getBehaviorClass())
                && Comparing.equal(getBehaviourMethodName(element), runConfiguration.getBehaviourMethod());
    }

    private String getBehaviourMethodName(PsiElement element) {
        PsiMethod method = getBehaviourMethodElement(element);
        return method == null ? null : method.getName();
    }

    public PsiClass getBehaviorClass(PsiElement element) {
        for (PsiElement current = element; current != null; current = current.getParent()) {
            if (current instanceof PsiClass) {
                PsiClass currentClass = (PsiClass) current;
                if (isBehaviorClass(currentClass)) {
                    return currentClass;
                }
            } else if (current instanceof PsiFile) {
                PsiClass psiClass = getMainClass((PsiFile) current);
                if (psiClass != null && isBehaviorClass(psiClass)) {
                    return psiClass;
                }
            }
        }
        PsiFile file = element.getContainingFile();
        if (file instanceof PsiJavaFile) {
            PsiClass[] definedClasses = ((PsiJavaFile) file).getClasses();
            for (int i = 0; i < definedClasses.length; i++) {
                PsiClass definedClass = definedClasses[i];
                if (isBehaviorClass(definedClass)) {
                    return definedClass;
                }
            }
            for (int i = 0; i < definedClasses.length; i++) {
                PsiClass definedClass = definedClasses[i];
                PsiClass behaviorCandidate = checkIfThereIsOneWithBehaviorAtEnd(definedClass);
                if (behaviorCandidate != null && isBehaviorClass(behaviorCandidate)) {
                    return behaviorCandidate;
                }
            }
        }
        return null;
    }

    private PsiClass checkIfThereIsOneWithBehaviorAtEnd(PsiClass psiClass) {
        String nameToCheck = psiClass.getQualifiedName() + "Behavior";
        Project project = psiClass.getProject();
        return PsiManager.getInstance(project).findClass(nameToCheck, GlobalSearchScope.allScope(project));
    }

    private PsiClass getMainClass(PsiFile psiFile) {
        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile javaFile = (PsiJavaFile) psiFile;
            PsiClass[] definedClasses = javaFile.getClasses();
            for (int i = 0; i < definedClasses.length; i++) {
                PsiClass definedClass = definedClasses[i];
                if (isPublic(definedClass)) {
                    return definedClass;
                }
            }
        }
        return null;
    }

    private boolean isBehaviorClass(PsiClass psiClass) {
        if (!isRunnableClass(psiClass)) {
            return false;
        }
        return implementsBehaviorsInterface(psiClass) || containsShouldMethod(psiClass);
    }

    private boolean implementsBehaviorsInterface(PsiClass psiClass) {
        PsiClassType[] interfaces = psiClass.getImplementsListTypes();
        for (int i = 0; i < interfaces.length; i++) {
            PsiClassType anInterface = interfaces[i];
            if ("jbehave.core.behaviour.Behaviours".equals(anInterface.resolve().getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsShouldMethod(PsiClass psiClass) {
        PsiMethod[] methods = psiClass.getAllMethods();
        for (int i = 0; i < methods.length; i++) {
            if (isBehaviorMethod(methods[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isRunnableClass(PsiClass psiClass) {
        return isPublic(psiClass) && !psiClass.isInterface() && !isAbstract(psiClass);
    }

    private boolean isAbstract(PsiModifierListOwner definition) {
        return definition.getModifierList().hasModifierProperty("abstract");
    }

    private boolean isBehaviorMethod(PsiMethod method) {
        return method.getName().startsWith("should") && isPublic(method) && isVoid(method);
    }

    private boolean isPublic(PsiModifierListOwner definition) {
        PsiModifierList modifierList = definition.getModifierList();
        return modifierList != null && modifierList.hasModifierProperty("public");
    }

    private boolean isVoid(PsiMethod method) {
        return PsiType.VOID.equals(method.getReturnType());
    }

    public RunnerAndConfigurationSettings createConfigurationByLocation(Location location) {
        PsiClass behaviorClass = getBehaviorClass(location.getPsiElement());
        if (behaviorClass == null) {
            return null;
        }
        return new JBehaveConfigurationProducer(behaviorClass, this).createProducer(location, null).getConfiguration();
    }

    public static JBehaveConfigurationType getInstance() {
        return ApplicationManager.getApplication().getComponent(JBehaveConfigurationType.class);
    }

    public PsiMethod getBehaviourMethodElement(PsiElement psiElement) {
        if (psiElement instanceof PsiIdentifier && psiElement.getParent() instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) psiElement.getParent();
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
        return null;
    }
}

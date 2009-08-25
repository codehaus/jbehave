package org.jbehave.plugin.idea.selena;

import java.util.Arrays;
import java.util.Collection;

import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.configurations.coverage.CoverageEnabledConfiguration;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.junit.RefactoringListeners;
import com.intellij.execution.junit.SourceScope;
import com.intellij.execution.runners.RunnerInfo;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.util.PathUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveConfiguration extends CoverageEnabledConfiguration implements RunJavaConfiguration
{
  protected ConfigurationData data;
  protected transient Project project;
  public boolean ALTERNATIVE_JRE_PATH_ENABLED;

  public String ALTERNATIVE_JRE_PATH;
  public static final String DEFAULT_PACKAGE_NAME = ExecutionBundle.message("default.package.presentable.name");
  public static final String DEFAULT_PACKAGE_CONFIGURATION_NAME = ExecutionBundle.message("default.package.configuration.name");

  private final RefactoringListeners.Accessor<PsiPackage> myPackage = new RefactoringListeners.Accessor<PsiPackage>()
  {
    public void setName(final String qualifiedName) {
      final boolean generatedName = isGeneratedName();
      data.setPackageName(qualifiedName);
      if (generatedName) setGeneratedName();
    }

    @Nullable
    public PsiPackage getPsiElement() {
      final String qualifiedName = data.getPackageName();
      return qualifiedName != null ? PsiManager.getInstance(getProject()).findPackage(qualifiedName) : null;
    }

    public void setPsiElement(final PsiPackage psiPackage) {
      setName(psiPackage.getQualifiedName());
    }
  };
  private final RefactoringListeners.Accessor<PsiClass> myClass = new RefactoringListeners.Accessor<PsiClass>()
  {
    public void setName(final String qualifiedName) {
      final boolean generatedName = isGeneratedName();
      data.setBehaviorClass(qualifiedName);
      if (generatedName) setGeneratedName();
    }

    @Nullable
    public PsiClass getPsiElement() {
      final String qualifiedName = data.getBehaviorClass();
      return qualifiedName != null ? PsiManager.getInstance(getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(project)) : null;
    }

    public void setPsiElement(final PsiClass psiClass) {
      setName(psiClass.getQualifiedName());
    }
  };

  public JBehaveConfiguration(String name, Project project, ConfigurationFactory factory) {
    this(name, project, new ConfigurationData(), factory);
  }

  private JBehaveConfiguration(String name, Project project, ConfigurationData data, ConfigurationFactory factory) {
    super(name, new RunConfigurationModule(project, false), factory);
    this.data = data;
    this.project = project;
  }

  public RunProfileState getState(DataContext dataContext, RunnerInfo runnerInfo, RunnerSettings runnerSettings, ConfigurationPerRunnerSettings configurationPerRunnerSettings) {
    CommandLineState state = new CommandLineState(this, runnerSettings, configurationPerRunnerSettings);
    state.setConsoleBuilder(TextConsoleBuilderFactory.getInstance().createBuilder(getProject()));
    state.setModulesToCompile(getModules());
    return state;
  }

  public ConfigurationData getData() {
    return data;
  }

  @NotNull
  public String getCoverageFileName() {
    final String name = getGeneratedName();
    if (name.equals(DEFAULT_PACKAGE_NAME)) {
      return DEFAULT_PACKAGE_CONFIGURATION_NAME;
    }
    return name;
  }

  protected boolean isMergeDataByDefault() {
    return false;
  }

  @Override
  protected ModuleBasedConfiguration createInstance() {
    return new JBehaveConfiguration(getName(), getProject(), data.copy(),
                                    JBehaveConfigurationType.getInstance().getConfigurationFactories()[0]);
  }

  @Override
  public Collection<Module> getValidModules() {
    //TODO add handling for package
    return RunConfigurationModule.getModulesForClass(getProject(), data.getBehaviorClass());
  }

  @Override
  public boolean isGeneratedName() {
    return data.isGeneratedName(getName());
  }

  @Override
  public String suggestedName() {
    return data.suggestName();
  }

  public void setProperty(int type, String value) {
    switch (type) {
      case RunJavaConfiguration.PROGRAM_PARAMETERS_PROPERTY:
        break;

      case RunJavaConfiguration.VM_PARAMETERS_PROPERTY:
        data.setVmParameters(value);
        break;

      case RunJavaConfiguration.WORKING_DIRECTORY_PROPERTY:
        setWorkingDirectoryPath(value);
        break;
    }
  }

  public String getProperty(int type) {
    switch (type) {
      case RunJavaConfiguration.PROGRAM_PARAMETERS_PROPERTY:
        return "";

      case RunJavaConfiguration.VM_PARAMETERS_PROPERTY:
        return data.getVmParameters();

      case RunJavaConfiguration.WORKING_DIRECTORY_PROPERTY:
        return getWorkingDirectoryPath();
    }
    throw new IllegalArgumentException("unknow property " + type);
  }

  public void setClassConfiguration(PsiClass psiclass) {
    setModule(ExecutionUtil.findModule(psiclass));
    data.setBehaviorClass(psiclass.getQualifiedName());
    setGeneratedName();
  }

  public void setPackageConfiguration(Module module, PsiPackage pkg) {
    data.setPackageName(pkg.getQualifiedName());
    setModule(module);
    setGeneratedName();
  }

  public void setMethodConfiguration(Location<PsiMethod> location) {
    PsiClass psiClass = location.getParentElement(PsiClass.class);
    setClassConfiguration(psiClass);
    data.setBehaviorMethod(location.getPsiElement().getName());
    setGeneratedName();
  }

  public void setGeneratedName() {
    setName(getGeneratedName());
  }

  public String getGeneratedName() {
    return data.suggestName();
  }

  public void setModule(Module module) {
    super.setModule(module);
    data.setModuleName(module.getName());
  }

  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    SettingsEditorGroup<JBehaveConfiguration> group = new SettingsEditorGroup<JBehaveConfiguration>();
    group.addEditor(ExecutionBundle.message("run.configuration.configuration.tab.title"), new JBehaveConfigurationEditor(getProject()));
    group.addEditor(ExecutionBundle.message("coverage.tab.title"), new JBehaveCoverageConfigurationEditor(getProject()));
    group.addEditor(ExecutionBundle.message("logs.tab.title"), new LogConfigurationPanel());
    return group;
  }

  public boolean needAdditionalConsole() {
    return false;
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {
    final SourceScope scope = data.getScope().getSourceScope(this);
    if (scope == null) {
      throw new RuntimeConfigurationException("Invalid scope specified");
    }
    PsiClass psiClass = PsiManager.getInstance(project).findClass(data.getBehaviorClass(), scope.getGlobalSearchScope());
    if (psiClass == null) {
      throw new RuntimeConfigurationException("Invalid class '" + data.getBehaviorClass() + "'specified");
    }
    if (data.isMethodSpecified()) {
      PsiMethod[] methods = psiClass.findMethodsByName(data.getBehaviourMethod(), true);
      if (methods.length == 0) {
        throw new RuntimeConfigurationException("Invalid method '" + data.getBehaviourMethod() + "'specified");
      }
      boolean foundSuitableMethod = false;
      for (PsiMethod method : methods) {
        if (method.getParameterList().getParametersCount() == 0) {
          foundSuitableMethod = true;
          break;
        }
      }
      if (!foundSuitableMethod) {
        throw new RuntimeConfigurationException("Method specified " + data.getBehaviourMethod() + " should not have any parameter");
      }
    }
  }

  @Override
  public void readExternal(Element element)
      throws InvalidDataException {
    super.readExternal(element);
    RunConfigurationField.readFromElement(data, element);
  }

  @Override
  public void writeExternal(Element element)
      throws WriteExternalException {
    super.writeExternal(element);
    RunConfigurationField.writeToElement(data, element);
  }

  @Nullable
  public RefactoringElementListener getRefactoringElementListener(final PsiElement element) {
    if (data.isMethodSpecified()) {
      if (!(element instanceof PsiMethod)) {
        return RefactoringListeners.getClassOrPackageListener(element, myClass);
      }
      final PsiMethod method = (PsiMethod) element;
      if (!method.getName().equals(data.getBehaviourMethod())) return null;
      if (!method.getContainingClass().equals(myClass.getPsiElement())) return null;
      return new RefactoringElementListener()
      {
        public void elementMoved(PsiElement newElement) {
          setMethod((PsiMethod) newElement);
        }

        public void elementRenamed(PsiElement newElement) {
          setMethod((PsiMethod) newElement);
        }

        private void setMethod(PsiMethod psiMethod) {
          boolean needToChangeName = isGeneratedName();
          data.setBehaviorMethod(psiMethod.getName());
          if (needToChangeName) {
            setName(data.suggestName());
          }
        }
      };
    } else {
      if (element instanceof PsiClass) {
        return RefactoringListeners.getClassOrPackageListener(element, myClass);
      }
    }
    return null;
  }

  public void restoreOriginalModule(final Module originalModule) {
    if (originalModule == null) return;
    final Module[] classModules = getModules();
    final Collection<Module> modules = ModuleUtil.collectModulesDependsOn(Arrays.asList(classModules));
    if (modules.contains(originalModule)) setModule(originalModule);
  }

  public void setWorkingDirectoryPath(String path) {
    path = PathUtil.getCanonicalPath(path);
    if (path.equals(projectBasePath())) {
      path = null;
    }
    data.setWorkingPath(path);
  }

  public String getWorkingDirectoryPath() {
    String value = data.getWorkingPath();
    return value == null ? projectBasePath() : value;
  }

  private String projectBasePath() {
    return PathUtil.getCanonicalPath(getProject().getBaseDir().getPath());
  }
}
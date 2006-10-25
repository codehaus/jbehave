package jbehave.plugin.idea;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.filters.TextConsoleBuidlerFactoryImpl;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.JavaProgramRunner;
import com.intellij.execution.runners.RunnerInfo;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.PathUtil;
import org.jdom.Element;

import java.util.Arrays;
import java.util.Collection;

public class JBehaveRunConfiguration extends RuntimeConfiguration {
    public String behaviorClass;
    public String behaviorMethod;
    public String moduleName;

    protected JBehaveRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(name, project, factory);
    }

    public SettingsEditor<JBehaveRunConfiguration> getConfigurationEditor() {
        return new JBehaveRunSettingsEditor(getProject());
    }

    public SettingsEditor<JDOMExternalizable> getRunnerSettingsEditor(JavaProgramRunner runner) {
        return null;
    }

    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        DefaultJDOMExternalizer.readExternal(this, element);
    }

    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    public RunProfileState getState(DataContext context, RunnerInfo runnerInfo, RunnerSettings runner, ConfigurationPerRunnerSettings configuration) throws ExecutionException {
        JBehaveCommandLineState commandLineState = new JBehaveCommandLineState(this, runner, configuration);
        commandLineState.setConsoleBuilder(TextConsoleBuidlerFactoryImpl.getInstance().createBuilder(getProject()));
        commandLineState.setModulesToCompile(getModules());
        return commandLineState;
    }

    public void checkConfiguration() throws RuntimeConfigurationException {
        super.checkConfiguration();
    }

    public Module[] getModules() {
        return ModuleManager.getInstance(getProject()).getModules();
    }

    public Module getModule() {
        return lookUp(getModules(), moduleName);
    }

    private Module lookUp(Module[] modules, String moduleName) {
        for (Module module : modules) {
            if (module.getName().equals(moduleName)) {
                return module;
            }
        }
        return modules.length > 0 ? modules[0] : null;
    }

    public void setModule(Module module) {
        moduleName = module == null ? null : module.getName();
    }

    public String getWorkingDirectoryPath() {
        return PathUtil.getCanonicalPath(getProject().getProjectFile().getParent().getPath());
    }

    public String getBehaviorClass() {
        return behaviorClass == null ? "" : behaviorClass;
    }

    public void setBehaviorClass(String behaviorClass) {
        this.behaviorClass = behaviorClass;
    }

    public Collection<Module> getValidModules() {
        return Arrays.asList(ModuleManager.getInstance(getProject()).getModules());
    }

    protected JBehaveRunConfiguration createInstance() {
        return new JBehaveRunConfiguration(getProject(), getFactory(), getName());
    }

    public String getBehaviourMethod() {
        return behaviorMethod == null ? "" : behaviorMethod;
    }

    public void setBehaviorMethod(String methodName) {
        behaviorMethod = methodName;
    }
}

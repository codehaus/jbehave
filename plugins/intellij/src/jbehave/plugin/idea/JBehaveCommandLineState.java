package jbehave.plugin.idea;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunnerSettings;

public class JBehaveCommandLineState extends JavaCommandLineState {
    private JBehaveRunConfiguration runConfiguration;

    JBehaveCommandLineState(JBehaveRunConfiguration runConfiguration, RunnerSettings runner, ConfigurationPerRunnerSettings configuration) {
        super(runner, configuration);
        this.runConfiguration = runConfiguration;
    }

    protected JavaParameters createJavaParameters() throws ExecutionException {
        JavaParameters parameters = new JavaParameters();
        parameters.setMainClass("org.jbehave.core.Run");
        String behaviorClass = runConfiguration.getBehaviorClass();
        String behaviourMethod = runConfiguration.getBehaviourMethod();
        String behaviourLocator = behaviorClass;
        if (behaviourMethod.length() > 0) {
            behaviourLocator += ":" + behaviourMethod;
        }
        parameters.getProgramParametersList().addParametersString(behaviourLocator);
        parameters.configureByModule(runConfiguration.getModule(),
                JavaParameters.JDK_AND_CLASSES_AND_TESTS);
        parameters.setWorkingDirectory(runConfiguration.getWorkingDirectoryPath());
        return parameters;
    }
}

package org.jbehave.plugin.idea.selena;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.*;
import org.jbehave.plugin.idea.Constants;

public class CommandLineState extends JavaCommandLineState
{
  private JBehaveConfiguration config;

  CommandLineState(JBehaveConfiguration configurationData, RunnerSettings runner, ConfigurationPerRunnerSettings configuration) {
    super(runner, configuration);
    this.config = configurationData;
  }

  protected JavaParameters createJavaParameters() throws ExecutionException {
    JavaParameters parameters = new JavaParameters();
    parameters.setMainClass(Constants.JBEHAVE_RUNNER_CLASS);
    ConfigurationData data = config.getData();
    String behaviorClass = data.getBehaviorClass();
    String behaviourMethod = data.getBehaviourMethod();
    String behaviourLocator = behaviorClass;
    if (behaviourMethod.length() > 0) {
      behaviourLocator += ":" + behaviourMethod;
    }
    parameters.getProgramParametersList().addParametersString(behaviourLocator);
    parameters.configureByModule(data.getModule(config.getProject()), JavaParameters.JDK_AND_CLASSES_AND_TESTS);
    parameters.setWorkingDirectory(config.getWorkingDirectoryPath());
    return parameters;
  }
}
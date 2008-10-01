package org.jbehave.ant;

import org.apache.tools.ant.BuildException;
import org.jbehave.scenario.RunnableScenario;

/**
 * Ant task that runs scenarios
 * 
 * @author Mauro Talevi
 */
public class ScenarioRunnerTask extends AbstractScenarioTask {

    public void execute() throws BuildException {
        for (RunnableScenario scenario : scenarios()) {
            try {
                log("Running scenario " + scenario.getClass().getName());
                scenario.runScenario();
            } catch (Throwable e) {
                throw new BuildException("Failed to run scenario " + scenario.getClass().getName(), e);
            }
        }
 
    }

        
}

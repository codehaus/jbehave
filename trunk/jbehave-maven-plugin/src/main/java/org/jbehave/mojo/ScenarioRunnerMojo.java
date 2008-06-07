package org.jbehave.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.scenario.Scenario;

/**
 * Mojo to run scenarios
 * 
 * @author Mauro Talevi
 * @goal run-scenarios
 */
public class ScenarioRunnerMojo extends AbstractScenarioMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        for (Scenario scenario : scenarios()) {
            try {
                getLog().info("Running scenario " + scenario.getClass().getName());
                scenario.runUsingSteps();
            } catch (Throwable e) {
                throw new MojoExecutionException("Failed to run scenario " + scenario.getClass().getName(), e);
            }
        }
    }

}

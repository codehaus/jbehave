package org.jbehave.mojo;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Mojo to run scenarios
 * 
 * @author Mauro Talevi
 * @goal run-scenarios
 */
public class ScenarioRunnerMojo extends AbstractJBehaveMojo {

    /**
     * Scenario class names
     * 
     * @parameter
     * @required
     */
    private List<String> scenarioClassNames;

    public void execute() throws MojoExecutionException, MojoFailureException {
        for (String scenarioClassName : scenarioClassNames) {
            try {
                System.out.println("Running scenario "+scenarioClassName);
                createScenarioClassLoader().newScenario(scenarioClassName).runUsingSteps();
            } catch (Throwable e) {
                throw new MojoExecutionException("Failed to run scenario " + scenarioClassName, e);
            }
        }
    }

}

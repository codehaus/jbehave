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
     */
    private List<String> scenarioClassNames;

    public void execute() throws MojoExecutionException, MojoFailureException {
        List<String> scenarios = scenarioClassNames;
        if ( scenarios == null || scenarios.isEmpty() ){
            scenarios = findScenarioClassNames();
        }
        for (String scenario : scenarios) {
            try {
                getLog().info("Running scenario "+scenario);
                createScenarioClassLoader().newScenario(scenario).runUsingSteps();
            } catch (Throwable e) {
                throw new MojoExecutionException("Failed to run scenario " + scenario, e);
            }
        }
    }

}

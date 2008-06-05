package org.jbehave.mojo;


import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Mojo to run scenarios 
 * 
 * @author Mauro Talevi
 * @goal run-scenario
 */
public class ScenarioRunnerMojo extends AbstractJBehaveMojo {
      
    /**
     * Scenario class name
     * 
     * @parameter 
     * @required
     */
    private String scenarioClassName;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            createScenarioClassLoader().newScenario(scenarioClassName).runUsingSteps();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new MojoExecutionException("Failed to run scenario "+scenarioClassName, e);
        }
    }


}

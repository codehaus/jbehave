package org.jbehave.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.scenario.RunnableScenario;

/**
 * Mojo to run scenarios
 * 
 * @author Mauro Talevi
 * @goal run-scenarios
 */
public class ScenarioRunnerMojo extends AbstractScenarioMojo {

    /**
     * The boolean flag to skip running scenario
     * 
     * @parameter default-value="false"
     */
    private boolean skip;
    
    /**
     * The boolean flag to ignoreFailure
     * 
     * @parameter default-value="false"
     */
    private boolean ignoreFailure;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        if ( skip ){
            getLog().info("Skipped running scenarios");
            return;
        }
        for (RunnableScenario scenario : scenarios()) {
            String scenarioName = scenario.getClass().getName();
            try {
				getLog().info("Running scenario " + scenarioName);
                scenario.runScenario();
            } catch (Throwable e) {
                String message = "Failed to run scenario " + scenarioName;
                if ( ignoreFailure ){
                    getLog().warn(message, e);
                } else {
                    throw new MojoExecutionException(message, e);
                }
            }
        }
    }

}

package org.jbehave.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.scenario.RunnableScenario;

/**
 * Mojo to generate stepdocs
 * 
 * @author Mauro Talevi
 * @goal stepdoc
 */
public class StepdocMojo extends AbstractScenarioMojo {

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
		if (skip) {
			getLog().info("Skipped generating stepdoc");
			return;
		}
		for (RunnableScenario scenario : scenarios()) {
			String scenarioName = scenario.getClass().getName();
			try {
				getLog().info("Generating stepdoc for " + scenarioName);
				scenario.generateStepdoc();
			} catch (Throwable e) {
				String message = "Failed to generate stepdoc for "+scenarioName;						
				if (ignoreFailure) {
					getLog().warn(message, e);
				} else {
					throw new MojoExecutionException(message, e);
				}
			}
		}
	}

}

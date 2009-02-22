package org.jbehave.mojo;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.reporters.PrintStreamStepDocReporter;
import org.jbehave.scenario.reporters.StepDocReporter;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.DefaultStepDocGenerator;
import org.jbehave.scenario.steps.StepDoc;
import org.jbehave.scenario.steps.StepDocGenerator;

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

	private StepDocGenerator generator = new DefaultStepDocGenerator();
	private StepDocReporter reporter = new PrintStreamStepDocReporter();

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip) {
			getLog().info("Skipped generating stepdoc");
			return;
		}
		for (RunnableScenario scenario : scenarios()) {
			String scenarioName = scenario.getClass().getName();
			try {
				getLog().info("Generating stepdoc for " + scenarioName);
				for (CandidateSteps steps : scenario.getSteps()) {
					List<StepDoc> stepdocs = generator.generate(steps.getClass());
					reporter.report(stepdocs);
				}
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

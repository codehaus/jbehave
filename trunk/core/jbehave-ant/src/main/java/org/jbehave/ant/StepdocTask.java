package org.jbehave.ant;

import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.reporters.PrintStreamStepDocReporter;
import org.jbehave.scenario.reporters.StepDocReporter;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.DefaultStepDocGenerator;
import org.jbehave.scenario.steps.StepDoc;
import org.jbehave.scenario.steps.StepDocGenerator;

/**
 * Ant task that generate stepdocs
 * 
 * @author Mauro Talevi
 */
public class StepdocTask extends AbstractScenarioTask {
	/**
	 * The boolean flag to skip running scenario
	 */
	private boolean skip = false;

	/**
	 * The boolean flag to ignoreFailure
	 */
	private boolean ignoreFailure = false;

	private StepDocGenerator generator = new DefaultStepDocGenerator();
	private StepDocReporter reporter = new PrintStreamStepDocReporter();
	
	public void execute() throws BuildException {
		if (skip) {
			log("Skipped running scenarios", MSG_INFO);
			return;
		}
		for (RunnableScenario scenario : scenarios()) {
			String scenarioName = scenario.getClass().getName();
			try {
				log("Generating stepdoc for " + scenarioName);
				for (CandidateSteps steps : scenario.getSteps()) {
					List<StepDoc> stepdocs = generator.generate(steps.getClass());
					reporter.report(stepdocs);
				}
			} catch (Throwable e) {
				String message = "Failed to generate stepdoc for " + scenarioName;
				if (ignoreFailure) {
					log(message, e, MSG_WARN);
				} else {
					throw new BuildException(message, e);
				}
			}
		}
	}

	// Setters
	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void setIgnoreFailure(boolean ignoreFailure) {
		this.ignoreFailure = ignoreFailure;
	}

}

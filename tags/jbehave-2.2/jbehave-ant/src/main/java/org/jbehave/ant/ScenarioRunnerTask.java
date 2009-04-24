package org.jbehave.ant;

import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;

import org.apache.tools.ant.BuildException;
import org.jbehave.scenario.RunnableScenario;

/**
 * Ant task that runs scenarios
 * 
 * @author Mauro Talevi
 */
public class ScenarioRunnerTask extends AbstractScenarioTask {
	/**
	 * The boolean flag to skip running scenario
	 */
	private boolean skip = false;

	/**
	 * The boolean flag to ignoreFailure
	 */
	private boolean ignoreFailure = false;

	public void execute() throws BuildException {
		if (skip) {
			log("Skipped running scenarios", MSG_INFO);
			return;
		}
		for (RunnableScenario scenario : scenarios()) {
			String scenarioName = scenario.getClass().getName();
			try {
				log("Running scenario " + scenarioName);
				scenario.runScenario();
			} catch (Throwable e) {
				String message = "Failed to run scenario " + scenarioName;
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

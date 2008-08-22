package org.jbehave;

import org.jbehave.scenario.ScenarioReporter;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.StepCreator;

/**
 * Provides techniques for defining, parsing and reporting scenarios.
 * 
 * @author Elizabeth Keogh
 */
public interface Configuration {

	ScenarioDefiner forDefiningScenarios();

	ScenarioReporter forReportingScenarios();

	PendingStepStrategy forPendingSteps();

	StepCreator forCreatingSteps();

	ErrorStrategy forHandlingErrors();

}

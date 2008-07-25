package org.jbehave;

import org.jbehave.scenario.ScenarioReporter;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.StepParser;

/**
 * Provides techniques for defining, parsing and reporting scenarios.
 * 
 * @author Elizabeth Keogh
 */
public interface Technique {

	ScenarioDefiner forDefiningScenarios();
	
	StepParser forParsingSteps();

	ScenarioReporter forReportingScenarios();

}

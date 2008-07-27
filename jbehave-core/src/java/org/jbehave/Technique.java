package org.jbehave;

import org.jbehave.scenario.ScenarioReporter;
import org.jbehave.scenario.parser.ScenarioDefiner;

/**
 * Provides techniques for defining, parsing and reporting scenarios.
 * 
 * @author Elizabeth Keogh
 */
public interface Technique {

	ScenarioDefiner forDefiningScenarios();

	ScenarioReporter forReportingScenarios();

}

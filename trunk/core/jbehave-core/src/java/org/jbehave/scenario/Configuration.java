package org.jbehave.scenario;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.reporters.Stepdoc2Reporter;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.Stepdoc2Generator;

/**
 * Provides the configuration with which JBehave runs.
 * 
 * Anyone wishing to customize JBehave may wish
 * to inject a new Configuration into their Scenario.
 * 
 * NB: This class may change dynamically, so any other class
 * wishing to use this should store the whole configuration,
 * and use the respective parts of it at runtime, rather
 * than attempting to store any part of it when the configuration
 * is provided.
 * 
 * @author Elizabeth Keogh
 */
public interface Configuration {

    ScenarioDefiner forDefiningScenarios();

    ScenarioReporter forReportingScenarios();

    PendingErrorStrategy forPendingSteps();

    StepCreator forCreatingSteps();

    ErrorStrategy forHandlingErrors();
    
    KeyWords keywords();

	Stepdoc2Generator forGeneratingStepdoc();

	Stepdoc2Reporter forReportingStepdoc();

}

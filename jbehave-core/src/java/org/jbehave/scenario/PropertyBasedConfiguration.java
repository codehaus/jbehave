package org.jbehave.scenario;

import org.jbehave.Configuration;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PassSilentlyDecorator;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.UnmatchedToPendingStepCreator;

public class PropertyBasedConfiguration implements Configuration {

    public static final String FAIL_ON_PENDING = "org.jbehave.failonpending";
    public static String OUTPUT_ALL = "org.jbehave.outputall";

    public ScenarioReporter forReportingScenarios() {
        if (System.getProperty(OUTPUT_ALL) == null) {
            return new PassSilentlyDecorator(new PrintStreamScenarioReporter());
        } else {
            return new PrintStreamScenarioReporter();
        }
    }

    public ScenarioDefiner forDefiningScenarios() {
        return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(), new PatternScenarioParser());
    }

    public PendingStepStrategy forPendingSteps() {
        if (System.getProperty(FAIL_ON_PENDING) == null) {
            return PendingStepStrategy.PASSING;
        }
        return PendingStepStrategy.FAILING;
    }

    public StepCreator forCreatingSteps() {
        return new UnmatchedToPendingStepCreator();
    }

    public ErrorStrategy forHandlingErrors() {
        return ErrorStrategy.RETHROW;
    }    
}

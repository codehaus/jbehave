package org.jbehave.scenario;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.ScenarioGivenWhenThenAnd;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.reporters.PassSilentlyDecorator;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.PrintStreamStepdoc2Reporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.reporters.Stepdoc2Reporter;
import org.jbehave.scenario.steps.DefaultStepDocGenerator;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.Stepdoc2Generator;
import org.jbehave.scenario.steps.UnmatchedToPendingStepCreator;

/**
 * The default configuration for JBehave's scenario runner. Works
 * for most things that users want to do.
 */
public class MostUsefulConfiguration implements Configuration {

    /**
     * Provides pending steps where unmatched steps exist.
     */
    public StepCreator forCreatingSteps() {
        return new UnmatchedToPendingStepCreator();
    }

    /**
     * Defines scenarios by looking for a file named after
     * the scenario and in the same package, using 
     * lower-case and underscores in place
     * of the camel-cased name - so MyScenario.java maps to
     * my_scenario.
     */
    public ScenarioDefiner forDefiningScenarios() {
        return new ClasspathScenarioDefiner(new PatternScenarioParser(this));
    }

    /**
     * Handles errors by rethrowing them.
     * 
     * <p>If there are multiple scenarios in a single story definition,
     * this could cause the story to stop after the first failing scenario.
     * 
     * <p>If you want different behaviour, you might want to look at the
     * ErrorStrategyInWhichWeTrustTheReporter.
     */
    public ErrorStrategy forHandlingErrors() {
        return ErrorStrategy.RETHROW;
    }

    /**
     * Allows pending steps to pass, so that builds etc. will not fail.
     * 
     * <p>If you want to spot pending steps, you might want to look at
     * PendingStepStrategy.FAILING, or alternatively at the
     * PropertyBasedConfiguration which provides a mechanism for
     * altering this behaviour in different environments.
     */
    public PendingErrorStrategy forPendingSteps() {
        return PendingErrorStrategy.PASSING;
    }

    /**
     * Reports failing or pending scenarios to System.out, while silently
     * passing scenarios.
     * 
     * <p>If you want different behaviour, you might like to use the
     * PrintStreamScenarioReporter, or look at the PropertyBasedConfiguration
     * which provides a mechanism for altering this behaviour in different
     * environments.
     */
    public ScenarioReporter forReportingScenarios() {
        return new PassSilentlyDecorator(new PrintStreamScenarioReporter());
    }
    
    /**
     * Provides the keywords Scenario, Given, When, Then and And.
     */
    public KeyWords keywords() {
        return new ScenarioGivenWhenThenAnd();
    }

	public Stepdoc2Generator forGeneratingStepdoc() {
		return new DefaultStepDocGenerator();
	}

	public Stepdoc2Reporter forReportingStepdoc() {
		return new PrintStreamStepdoc2Reporter();
	}

}

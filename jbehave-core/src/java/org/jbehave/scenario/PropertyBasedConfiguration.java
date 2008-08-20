package org.jbehave.scenario;

import org.jbehave.Configuration;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PassSilentlyDecorator;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.steps.PendingStepStrategy;

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
		return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(), new PatternStepParser());
	}

	public PendingStepStrategy forPendingSteps() {
		if (System.getProperty(FAIL_ON_PENDING) == null) {
			return PendingStepStrategy.PASSING;
		}
		return PendingStepStrategy.FAILING;
	}	
}

package org.jbehave;

import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;

public class OurTechnique implements Technique {

	public PatternStepParser forParsingSteps() {
		return new PatternStepParser();
	}

	public PrintStreamScenarioReporter forReportingScenarios() {
		return new PrintStreamScenarioReporter();
	}

	public ScenarioFileLoader forDefiningScenarios() {
		return new ScenarioFileLoader(new UnderscoredCamelCaseResolver());
	}	
}

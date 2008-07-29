package org.jbehave;

import org.jbehave.scenario.ScenarioReporter;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;

public class OurTechnique implements Technique {

	public ScenarioReporter forReportingScenarios() {
		return new PrintStreamScenarioReporter();
	}

	public ScenarioDefiner forDefiningScenarios() {
		return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(), new PatternStepParser());
	}	
}

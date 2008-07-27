package org.jbehave.scenario.parser;

import java.util.List;

public class ScenarioDefinition {

	private final StepParser stepParser;
	private final String scenarioAsString;

	public ScenarioDefinition(StepParser stepParser, String scenarioAsString) {
		this.stepParser = stepParser;
		this.scenarioAsString = scenarioAsString;
	}

	public List<String> getSteps() {
		return stepParser.findSteps(scenarioAsString);
	}

}

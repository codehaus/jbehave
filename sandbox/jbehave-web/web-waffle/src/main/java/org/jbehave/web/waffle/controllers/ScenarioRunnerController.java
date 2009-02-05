package org.jbehave.web.waffle.controllers;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.Steps;

public class ScenarioRunnerController {

	private final ScenarioParser scenarioParser;
	private final ScenarioRunner scenarioRunner;
	private final Steps steps;

	private ByteArrayOutputStream outputStream;
	private Configuration configuration;
	private ScenarioData scenarioData;

	public ScenarioRunnerController(Configuration configuration,
			ScenarioParser scenarioParser, ScenarioRunner scenarioRunner,
			Steps steps) {
		this.scenarioParser = scenarioParser;
		this.scenarioRunner = scenarioRunner;
		this.steps = steps;
		this.outputStream = new ByteArrayOutputStream();
		this.configuration = new PropertyBasedConfiguration(configuration) {
			@Override
			public ScenarioReporter forReportingScenarios() {
				return new PrintStreamScenarioReporter(new PrintStream(
						outputStream));
			}
		};
		this.scenarioData = new ScenarioData();
	}

	@ActionMethod(asDefault = true)
	public void run() {
		if (isNotBlank(scenarioData.getInput())) {
			try {
				outputStream.reset();
				scenarioRunner.run(storyDefinition(), configuration, steps);
				scenarioData.setOutput(outputStream.toString());
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	private StoryDefinition storyDefinition() {
		return scenarioParser.defineStoryFrom(scenarioData.getInput());
	}

	public ScenarioData getScenarioData() {
		return scenarioData;
	}

	public void setScenarioData(ScenarioData scenarioData) {
		this.scenarioData = scenarioData;
	}

}

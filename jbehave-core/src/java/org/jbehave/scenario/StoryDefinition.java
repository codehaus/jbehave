package org.jbehave.scenario;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jbehave.scenario.definition.Blurb;

public class StoryDefinition {

	private final Blurb blurb;
	private final List<ScenarioDefinition> scenarioDefinitions;

	public StoryDefinition(Blurb blurb, List<ScenarioDefinition> scenarioDefinitions) {
		this.blurb = blurb;
		this.scenarioDefinitions = scenarioDefinitions;
	}

	public StoryDefinition(List<ScenarioDefinition> scenarioDefinitions) {
		this(Blurb.EMPTY, scenarioDefinitions);
	}
	
	public StoryDefinition(Blurb blurb, ScenarioDefinition... scenarioDefinitions) {
		this(blurb, Arrays.asList(scenarioDefinitions));
	}
	
	public StoryDefinition(ScenarioDefinition... scenarioDefinitions) {
		this(Arrays.asList(scenarioDefinitions));
	}

	public Blurb getBlurb() {
		return blurb;
	}

	public List<ScenarioDefinition> getScenarios() {
		return Collections.unmodifiableList(scenarioDefinitions);
	}
}

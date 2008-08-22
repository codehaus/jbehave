package org.jbehave.scenario;

import java.util.Arrays;
import java.util.List;

public class ScenarioDefinition {

	private final List<String> steps;
	private final String title;

	public ScenarioDefinition(List<String> steps) {
		this("", steps);
	}

	public ScenarioDefinition(String title, List<String> steps) {
		this.title = title;
		this.steps = steps;
	}

	public ScenarioDefinition(String title, String... steps) {
		this(title, Arrays.asList(steps));
	}

	public List<String> getSteps() {
		return steps;
	}

	public String getTitle() {
		return title;
	}

}

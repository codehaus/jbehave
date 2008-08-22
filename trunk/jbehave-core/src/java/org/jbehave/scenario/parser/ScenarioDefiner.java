package org.jbehave.scenario.parser;

import java.util.List;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.StoryDefinition;

public interface ScenarioDefiner {

	StoryDefinition loadScenarioDefinitionsFor(Class<? extends Scenario> clazz);

}
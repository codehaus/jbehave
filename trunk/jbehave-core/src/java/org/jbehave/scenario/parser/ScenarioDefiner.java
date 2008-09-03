package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.definition.StoryDefinition;

public interface ScenarioDefiner {

    StoryDefinition loadScenarioDefinitionsFor(Class<? extends Scenario> scenarioClass);

}
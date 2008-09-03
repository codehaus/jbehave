package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.definition.StoryDefinition;

/**
 * <p>
 * Loads scenarios contained in a story from a given scenario class.
 * </p>
 */
public interface ScenarioDefiner {

    StoryDefinition loadScenarioDefinitionsFor(Class<? extends Scenario> scenarioClass);

}

package org.jbehave.scenario.parser;

import org.jbehave.scenario.definition.StoryDefinition;

/**
 * <p>
 * Parses the scenarios contained in a story from a textual representation.
 * </p>
 */
public interface ScenarioParser {

    StoryDefinition defineStoryFrom(String wholeStory);

}

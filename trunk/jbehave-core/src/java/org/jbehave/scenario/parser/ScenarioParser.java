package org.jbehave.scenario.parser;

import org.jbehave.scenario.definition.StoryDefinition;

public interface ScenarioParser {

    StoryDefinition defineStoryFrom(String wholeStory);

}

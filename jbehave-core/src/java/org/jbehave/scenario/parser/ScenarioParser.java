package org.jbehave.scenario.parser;

import org.jbehave.scenario.StoryDefinition;

public interface ScenarioParser {

    StoryDefinition defineStoryFrom(String wholeStory);

}

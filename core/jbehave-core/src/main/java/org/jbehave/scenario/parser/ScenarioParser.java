package org.jbehave.scenario.parser;

import org.jbehave.scenario.definition.StoryDefinition;

/**
 * <p>
 * Parses the scenarios contained in a story from a textual representation.
 * </p>
 */
public interface ScenarioParser {

    /**
     * Defines story from its textual representation
     * 
     * @param storyAsText the textual representation
     * @param storyPath the story path, may be <code>null</code> if not loaded from filesystem
     * @return The StoryDefinition
     */
    StoryDefinition defineStoryFrom(String storyAsText, String storyPath);

}

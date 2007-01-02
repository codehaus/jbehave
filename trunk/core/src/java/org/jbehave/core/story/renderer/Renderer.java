/*
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.renderer;

import org.jbehave.core.story.domain.Event;
import org.jbehave.core.story.domain.Given;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Outcome;
import org.jbehave.core.story.domain.Scenario;
import org.jbehave.core.story.domain.Story;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Renderer {
	void renderStory(Story story);
	void renderNarrative(Narrative narrative);
	void renderScenario(Scenario scenario);
	void renderGiven(Given given);
	void renderOutcome(Outcome outcome);
	void renderEvent(Event event);
    
    /**
     * This method can be used by scenario components overriding the
     * narrateTo method in order to render custom descriptions.
     */
    void render(Object obj);
}

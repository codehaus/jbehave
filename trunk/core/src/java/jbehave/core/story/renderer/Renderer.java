/*
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.renderer;

import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.Story;

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
}

/*
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.visitor;

import jbehave.core.story.domain.AcceptanceCriteria;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.Story;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitor {
	void visitStory(Story story);
	void visitNarrative(Narrative narrative);
	void visitAcceptanceCriteria(AcceptanceCriteria criteria);
	void visitScenario(Scenario scenario);
	void visitGiven(Given given);
	void visitOutcome(Outcome outcome);
	void visitEvent(Event event);
}

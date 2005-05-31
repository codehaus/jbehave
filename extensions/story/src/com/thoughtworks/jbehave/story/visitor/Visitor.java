/*
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.visitor;

import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Context;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcomes;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.Story;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitor {
	void visitStory(Story story);
	void visitNarrative(Narrative narrative);
	void visitAcceptanceCriteria(AcceptanceCriteria criteria);
	void visitScenario(Scenario scenario);
	void visitContext(Context context);
	void visitGiven(Given given);
	void visitEvent(Event event);
	void visitOutcome(Outcomes outcome);
	void visitExpectation(Outcome expectation);
}

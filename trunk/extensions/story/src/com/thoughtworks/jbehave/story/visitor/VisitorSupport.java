package com.thoughtworks.jbehave.story.visitor;

import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.Story;


public class VisitorSupport implements Visitor {

	public void visitStory(Story story) {
	}

	public void visitNarrative(Narrative narrative) {
	}

	public void visitAcceptanceCriteria(AcceptanceCriteria criteria) {
	}

	public void visitScenario(Scenario scenario) {
	}

	public void visitGiven(Given given) {
	}

	public void visitOutcome(Outcome outcome) {
	}

	public void visitEvent(Event event) {
	}
}

package jbehave.story.visitor;

import jbehave.story.domain.AcceptanceCriteria;
import jbehave.story.domain.Event;
import jbehave.story.domain.Given;
import jbehave.story.domain.Narrative;
import jbehave.story.domain.Outcome;
import jbehave.story.domain.Scenario;
import jbehave.story.domain.Story;


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

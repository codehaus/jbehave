package jbehave.core.story.visitor;

import jbehave.core.story.domain.Scenarios;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.Story;


public class VisitorSupport implements Visitor {

	public void visitStory(Story story) {
	}

	public void visitNarrative(Narrative narrative) {
	}

	public void visitScenarios(Scenarios criteria) {
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

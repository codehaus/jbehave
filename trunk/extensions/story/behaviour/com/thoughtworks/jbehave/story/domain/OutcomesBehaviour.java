package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

public class OutcomesBehaviour extends UsingMiniMock {
	public void shouldPassItselfAndComponentsToVisitorInCorrectOrder() throws Exception {
		// given...
		Visitor visitor = (Visitor) mock(Visitor.class);
		Mock expectation = mock(Outcome.class);
		Outcomes outcomes = new Outcomes((Outcome)expectation);

		// expect...
		expectation.expects("accept").with(visitor);
		
		// when...
		outcomes.accept((Visitor)visitor);
	}
}

package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

public class OutcomeBehaviour extends UsingMiniMock {
	public void shouldPassItselfAndComponentsToVisitorInCorrectOrder() throws Exception {
		// given
		Visitor visitor = (Visitor) mock(Visitor.class);
		Mock expectation = mock(Expectation.class);
		Outcome outcome = new Outcome((Expectation)expectation);

		expectation.expects("accept").with(visitor);
		
		// when
		outcome.accept((Visitor)visitor);
		
		// then
		verifyMocks();
	}
}

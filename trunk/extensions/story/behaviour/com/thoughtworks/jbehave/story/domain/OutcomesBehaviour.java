package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

public class OutcomesBehaviour extends UsingMiniMock {
	public void shouldTellComponentsToSetExpectationsInWorld() throws Exception {
		// given...
		Mock outcome1 = mock(Outcome.class, "outcome1");
		Mock outcome2 = mock(Outcome.class, "outcome2");
		World world = (World)stub(World.class);
		Outcome outcomes = new Outcomes(new Outcome[] {(Outcome) outcome1, (Outcome) outcome2});
		
		// expect...
		outcome1.expects("setExpectationIn").with(world);
		outcome2.expects("setExpectationIn").with(world).after(outcome1, "setExpectationIn");

		// when...
		outcomes.setExpectationIn(world);
	}

	public void shouldTellComponentsToVerifyWorld() throws Exception {
		// given...
		Mock outcome1 = mock(Outcome.class, "outcome1");
		Mock outcome2 = mock(Outcome.class, "outcome2");
		World world = (World)stub(World.class);
		Outcome outcomes = new Outcomes(new Outcome[] {(Outcome) outcome1, (Outcome) outcome2});
		
		// expect...
		outcome1.expects("verify").with(world);
		outcome2.expects("verify").with(world).after(outcome1, "verify");

		// when...
		outcomes.verify(world);
	}
}

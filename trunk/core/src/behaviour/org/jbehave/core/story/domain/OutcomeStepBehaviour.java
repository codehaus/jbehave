package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;

public class OutcomeStepBehaviour extends UsingMiniMock {
    public void shouldTellOutcomeToVerifyWorld() {
        // given
        Mock outcome = mock(Outcome.class);
        AbstractStep step = new OutcomeStep((Outcome)outcome);
        World world = new HashMapWorld();
        
        // expect
        outcome.expects("verify").with(world);
        
        // when
        step.perform(world);
        
        // then
        verifyMocks();
    }
}

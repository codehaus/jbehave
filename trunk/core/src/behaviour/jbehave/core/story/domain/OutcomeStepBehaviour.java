package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

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

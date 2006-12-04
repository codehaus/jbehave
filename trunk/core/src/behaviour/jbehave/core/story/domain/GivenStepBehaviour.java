package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

public class GivenStepBehaviour extends UsingMiniMock {
    public void shouldTellGivenToSetUpWorld() {
        // given
        Mock given = mock(Given.class);
        GivenStep step = new GivenStep((Given)given);
        World world = new HashMapWorld();
        
        // expect
        given.expects("setUp").with(world);
        
        // when
        step.perform(world);
        
        // then
        verifyMocks();
    }
}

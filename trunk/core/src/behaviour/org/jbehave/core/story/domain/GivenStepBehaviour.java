package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.domain.Given;
import org.jbehave.core.story.domain.GivenStep;
import org.jbehave.core.story.domain.HashMapWorld;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;

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

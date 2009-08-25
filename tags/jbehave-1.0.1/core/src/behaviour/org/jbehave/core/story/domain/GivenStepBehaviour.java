package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;

public class GivenStepBehaviour extends UsingMiniMock {
    public void shouldTellGivenToSetUpWorld() throws Exception {
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

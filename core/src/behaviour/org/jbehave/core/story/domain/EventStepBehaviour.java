package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;

public class EventStepBehaviour extends UsingMiniMock {
    public void shouldTellEventToOccurInWorld() throws Exception {
        // given
        Mock event = mock(Event.class);
        EventStep step = new EventStep((Event)event);
        World world = new HashMapWorld();
        
        // expect
        event.expects("occurIn").with(world);
        
        // when
        step.perform(world);
        
        // then
        verifyMocks();
    }
}

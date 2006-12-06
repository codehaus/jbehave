package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.domain.Event;
import org.jbehave.core.story.domain.EventStep;
import org.jbehave.core.story.domain.HashMapWorld;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;

public class EventStepBehaviour extends UsingMiniMock {
    public void shouldTellEventToOccurInWorld() {
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

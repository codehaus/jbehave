package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

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

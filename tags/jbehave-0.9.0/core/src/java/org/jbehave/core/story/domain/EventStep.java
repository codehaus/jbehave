package org.jbehave.core.story.domain;

public class EventStep extends AbstractStep {

    public EventStep(Event event) {
        super(event);
    }

    public void perform(World world) {
        ((Event)component).occurIn(world);
    }
}

package jbehave.core.story.domain;

import jbehave.core.story.renderer.Renderer;

public class EventOutcomeStep implements Step {

    private final Event event;
    private final Outcome outcome;

    public EventOutcomeStep(Event event, Outcome outcome) {
        this.event = event;
        this.outcome = outcome;
    }

    public void perform(World world) {
        event.occurIn(world);
        outcome.verify(world);
    }

    public void narrateTo(Renderer renderer) {
        event.narrateTo(renderer);
        outcome.narrateTo(renderer);
    }

    public boolean containsMocks() {
        return event.containsMocks() || outcome.containsMocks();
    }

    public void verifyMocks() {
        event.verifyMocks();
        outcome.verifyMocks();
    }

    public void undoIn(World world) {
        event.undoIn(world);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[EventOutcomeStep event=");
        buffer.append(event);
        buffer.append(", outcome=");
        buffer.append(outcome);
        buffer.append("]");
        return buffer.toString();
    }
}

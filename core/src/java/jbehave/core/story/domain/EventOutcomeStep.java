package jbehave.core.story.domain;

import jbehave.core.story.visitor.Visitor;

public class EventOutcomeStep implements Step {

    private final Event event;
    private final Outcome outcome;

    public EventOutcomeStep(Event event, Outcome outcome) {
        this.event = event;
        this.outcome = outcome;
    }

    public void perform(World world) {
        outcome.setExpectationIn(world);
        event.occurIn(world);
        outcome.verify(world);
    }

    public void accept(Visitor visitor) {
        event.accept(visitor);
        outcome.accept(visitor);
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

}

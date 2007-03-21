package org.jbehave.core.story.domain;

public class OutcomeStep extends AbstractStep {

    public OutcomeStep(Outcome outcome) {
        super(outcome);
    }

    public void perform(World world) throws Exception {
        outcome().verify(world);
    }

    private Outcome outcome() {
        return (Outcome)component;
    }
}

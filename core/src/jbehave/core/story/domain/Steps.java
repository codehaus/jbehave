package jbehave.core.story.domain;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;

public class Steps extends CompositeVisitableUsingMiniMock implements Step {

    public Steps(Step step) {
        this(new Step[] {step});
    }
    
    public Steps(Step stepA, Step stepB) {
        this(new Step[] { stepA, stepB });
    }
    
    public Steps(Step stepA, Step stepB, Step stepC) {
        this(new Step[] { stepA, stepB, stepC });
    }
    
    public Steps(Step[] steps) {
        super(steps);
    }

    public void perform(World world) {
        // We don't do this the same way as the other composites because 
        // of the odd order in which Outcome and Event do their stuff
        for (int i = 0; i < visitables.size(); i++) {
            ((Step)visitables.get(i)).perform(world);
        }
    }

}

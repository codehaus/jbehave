package jbehave.core.story.domain;

import jbehave.core.minimock.Mock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMockBehaviour;

public class StepsBehaviour extends CompositeVisitableUsingMiniMockBehaviour{
    
    protected CompositeVisitableUsingMiniMock newComposite(Object component1, Object component2) {
        return new Steps((Step) component1, (Step) component2);
    }
    
    protected Class componentType() { return Step.class; }

    public void shouldTellComponentsToPerformInWorld() throws Exception {
        // given...
        Mock step1 = mock(Step.class);
        Mock step2 = mock(Step.class);
        World world = (World)stub(World.class);
        Step steps = new Steps((Step) step1, (Step) step2);
        
        
        // expect...
        step1.expects("perform").with(world);
        step2.expects("perform").with(world).after(step1, "perform");

        // when...
        steps.perform(world);
    }
}

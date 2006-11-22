package jbehave.core.story.domain;

import jbehave.core.mock.Mock;

public class StepsBehaviour extends ScenarioComponentsBehaviour{
    
    protected ScenarioComponents newComposite(ScenarioComponent component1, ScenarioComponent component2) {
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
    
    public void shouldUndoComponentsInReverseOrder() {
        // given...
        Mock step1 = mock(Step.class);
        Mock step2 = mock(Step.class);
        World world = (World)stub(World.class);
        Step steps = new Steps((Step) step1, (Step) step2);
        
        
        // expect...
        step2.expects("undoIn").with(world);
        step1.expects("undoIn").with(world).after(step2, "undoIn");

        // when...
        steps.undoIn(world);
    }
}

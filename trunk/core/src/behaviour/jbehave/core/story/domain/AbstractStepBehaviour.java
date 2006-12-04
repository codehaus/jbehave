package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

public class AbstractStepBehaviour extends UsingMiniMock {
    
    public static class StubbedAbstractStep extends AbstractStep {
        public StubbedAbstractStep(ScenarioComponent component) {
            super(component);
        }
        public void perform(World world) {}
    }
    
    public void shouldDelegateNarration() {
        // given
        Mock component = mock(ScenarioComponent.class);
        AbstractStep step = new StubbedAbstractStep((ScenarioComponent)component);
        Renderer renderer = (Renderer) stub(Renderer.class);
        
        // expect
        component.expects("narrateTo").with(renderer);
        
        // when
        step.narrateTo(renderer);
        
        // then
        verifyMocks();
    }

    public void shouldDelegateVerifyingMocks() {
        // given
        Mock component = mock(ScenarioComponent.class);
        AbstractStep step = new StubbedAbstractStep((ScenarioComponent)component);
        
        // expect
        component.expects("verifyMocks");
        
        // when
        step.verifyMocks();
        
        // then
        verifyMocks();
    }
    
    public void shouldDelegateContainsMocks() {
        // given
        Mock component = mock(ScenarioComponent.class);
        AbstractStep step = new StubbedAbstractStep((ScenarioComponent)component);
        
        // expect
        component.expects("containsMocks").will(returnValue(false));
        
        // when
        step.containsMocks();
        
        // then
        verifyMocks();
    }
    
    public interface ScenarioComponentWithCleanUp extends ScenarioComponent, HasCleanUp {}
    
    public void shouldTellComponentWithCleanUpToCleanUpWorld() throws Exception {
        // given
        Mock component = mock(ScenarioComponentWithCleanUp.class);
        AbstractStep step = new StubbedAbstractStep((ScenarioComponent)component);
        World world = new HashMapWorld();
        
        // expect
        component.expects("cleanUp").with(world);
        
        // when
        step.cleanUp(world);
        
        // then
        verifyMocks();
    }
}

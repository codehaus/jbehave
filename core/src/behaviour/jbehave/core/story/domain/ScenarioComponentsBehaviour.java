package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

public abstract class ScenarioComponentsBehaviour extends UsingMiniMock {
	protected abstract ScenarioComponents newComposite(ScenarioComponent component1, ScenarioComponent component2);
	protected abstract Class componentType();
	
	protected Mock mockComponent(String componentName) {
		return mock(componentType(), componentName);
	}

    public void shouldMakeEachComponentNarrateToRenderer() throws Exception {
        // setup...
        Mock child1 = mockComponent("child1");
        Mock child2 = mockComponent("child2");
        Renderer renderer = (Renderer) stub(Renderer.class);
        
        ScenarioComponents list = newComposite((ScenarioComponent)child1, (ScenarioComponent)child2);
        
        // expect...
        child1.expects("narrateTo").with(renderer);
        child2.expects("narrateTo").with(renderer).after(child1, "narrateTo");

        // when...
        list.narrateTo(renderer);
        
        // then...
        verifyMocks();
    }
	
	public void shouldTellComponentsToVerifyTheirMocks() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
        ScenarioComponents composite = newComposite((ScenarioComponent)component1, (ScenarioComponent)component2);
		
		// expect...
		component1.expects("verifyMocks").withNoArguments();
		component2.expects("verifyMocks").withNoArguments().after(component1, "verifyMocks");

		// when...
		composite.verifyMocks();
        
        // then...
        verifyMocks();
	}
		
	public void shouldContainMocksIffAnyOfItsComponentsContainMocks() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
        Mock component3 = mockComponent("component3");
        ScenarioComponents compositeWithMocks = newComposite((ScenarioComponent)component1, (ScenarioComponent)component2);
        ScenarioComponents compositeWithoutMocks = newComposite((ScenarioComponent)component2, (ScenarioComponent)component3);
		
		component1.stubs("containsMocks").will(returnValue(true));
		component2.stubs("containsMocks").will(returnValue(false));
        component3.stubs("containsMocks").will(returnValue(false));

		// when...
		// then...
		ensureThat(compositeWithMocks.containsMocks(), eq(true));
        ensureThat(compositeWithoutMocks.containsMocks(), eq(false));
	}
}

package jbehave.core.story.visitor;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.Visitor;

public abstract class CompositeVisitableUsingMiniMockBehaviour extends UsingMiniMock {
	protected abstract CompositeVisitableUsingMiniMock newComposite(Object  component1, Object component2);
	protected abstract Class componentType();
	
	protected Mock mockComponent(String componentName) {
		return mock(componentType(), componentName);
	}

	public void shouldPassComponentsToVisitorInOrder() throws Exception {
		// given...
		Mock visitor = mock(Visitor.class);
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		CompositeVisitableUsingMiniMock composite = newComposite(component1, component2);

		// expect...
		component1.expects("accept").with(visitor);
		component2.expects("accept").with(visitor).after(component1, "accept");
		
		// when...
		composite.accept((Visitor)visitor);
        
        // then...
        verifyMocks();
	}
	
	public void shouldTellComponentsToVerifyTheirMocks() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		CompositeVisitableUsingMiniMock composite = newComposite(component1, component2);
		
		// expect...
		component1.expects("verifyMocks").withNoArguments();
		component2.expects("verifyMocks").withNoArguments().after(component1, "verifyMocks");

		// when...
		composite.verifyMocks();
        
        // then...
        verifyMocks();
	}
	
	public void shouldNotContainMocksIfNoneOfItsComponentsContainMocks() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		CompositeVisitableUsingMiniMock composite = newComposite(component1, component2);
		
		component1.stubs("containsMocks").will(returnValue(false));
		component2.stubs("containsMocks").will(returnValue(false));

		// when...
		boolean result = composite.containsMocks();
		
		// then...
		ensureThat(result, eq(false));
        verifyMocks();
	}
	
	public void shouldContainMocksIfAnyOfItsComponentsContainMocks() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		CompositeVisitableUsingMiniMock composite = newComposite(component1, component2);
		
		component1.stubs("containsMocks").will(returnValue(false));
		component2.stubs("containsMocks").will(returnValue(true));

		// when...
		boolean result = composite.containsMocks();
		
		// then...
		ensureThat(result, eq(true));
        verifyMocks();
	}
}

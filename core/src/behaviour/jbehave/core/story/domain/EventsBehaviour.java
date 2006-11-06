package jbehave.core.story.domain;

import jbehave.core.mock.Mock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMockBehaviour;


public class EventsBehaviour extends CompositeVisitableUsingMiniMockBehaviour {

	protected CompositeVisitableUsingMiniMock newComposite(Object component1, Object component2) {
		return new Events((Event) component1, (Event) component2);
	}
	
	protected Class componentType() { return Event.class; }

	public void shouldTellComponentsToOccurInOrder() {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		World world = (World)stub(World.class);
		Event events = new Events((Event) component1, (Event) component2);
			
		// expect...
		component1.expects("occurIn").with(world);
		component2.expects("occurIn").with(world).after(component1, "occurIn");

		// when...
		events.occurIn(world);
        
        // then...
        verifyMocks();
	} 
    
    public void shouldUndoComponentsInReverse() {
        // given...
        Mock component1 = mockComponent("component1");
        Mock component2 = mockComponent("component2");
        World world = (World)stub(World.class);
        Event events = new Events((Event) component1, (Event) component2);
                
        // expect...
        component2.expects("undoIn").with(world);
        component1.expects("undoIn").with(world).after(component2, "undoIn");

        // when...
        events.undoIn(world);
        
        // then...
        verifyMocks();
    }

}

package com.thoughtworks.jbehave.story.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.story.visitor.CompositeVisitableUsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.CompositeVisitableUsingMiniMockBehaviour;
import com.thoughtworks.jbehave.story.visitor.Visitor;

public class EventsBehaviour  extends CompositeVisitableUsingMiniMockBehaviour {

	protected CompositeVisitableUsingMiniMock newComposite(Object component1, Object component2) {
		return new Events((Event) component1, (Event) component2);
	}
	
	protected Class componentType() { return Event.class; }

	public void shouldTellComponentsToOccurInWorld() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		World world = (World)stub(World.class);
		Event events = new Events((Event) component1, (Event) component2);
		
		// do what a real Event would do
		InvocationHandler dispatchItselfToVisitor = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitEvent((Event)proxy);
				return null;
			}
		};
		component1.stubs("accept").with(a(Visitor.class)).will(dispatchItselfToVisitor);
		component2.stubs("accept").with(a(Visitor.class)).will(dispatchItselfToVisitor);
		
		// expect...
		component1.expects("occurIn").with(world);
		component2.expects("occurIn").with(world).after(component1, "occurIn");

		// when...
		events.occurIn(world);
	}

}

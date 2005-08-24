package com.thoughtworks.jbehave.story.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.story.visitor.CompositeVisitableUsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.CompositeVisitableUsingMiniMockBehaviour;
import com.thoughtworks.jbehave.story.visitor.Visitor;

public class GivensBehaviour extends CompositeVisitableUsingMiniMockBehaviour {

	protected CompositeVisitableUsingMiniMock newComposite(Object component1, Object component2) {
		return new Givens((Given) component1, (Given) component2);
	}
	
	protected Class componentType() { return Given.class; }

	public void shouldTellComponentsToSetUpWorld() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		World world = (World)stub(World.class);
		Given givens = new Givens((Given) component1, (Given) component2);
		
		// do what a real Given would do
		InvocationHandler dispatchItselfToVisitor = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitGiven((Given)proxy);
				return null;
			}
		};
		component1.stubs("accept").with(a(Visitor.class)).will(dispatchItselfToVisitor);
		component2.stubs("accept").with(a(Visitor.class)).will(dispatchItselfToVisitor);
		
		// expect...
		component1.expects("setUp").with(world);
		component2.expects("setUp").with(world).after(component1, "setUp");

		// when...
		givens.setUp(world);
	}
}

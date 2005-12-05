package jbehave.core.story.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import jbehave.core.minimock.Mock;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.World;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.CompositeVisitableUsingMiniMockBehaviour;
import jbehave.core.story.visitor.Visitor;


public class OutcomesBehaviour extends CompositeVisitableUsingMiniMockBehaviour {

	protected CompositeVisitableUsingMiniMock newComposite(Object component1, Object component2) {
		return new Outcomes(new Outcome[] {(Outcome) component1, (Outcome) component2});
	}
	
	protected Class componentType() { return Outcome.class; }
	
	public void shouldTellComponentsToSetExpectationsInWorld() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		World world = (World)stub(World.class);
		Outcome outcomes = new Outcomes(new Outcome[] {(Outcome) component1, (Outcome) component2});
		
		// do what a real Outcome would do
		InvocationHandler dispatchItselfToVisitor = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitOutcome((Outcome)proxy);
				return null;
			}
		};
		component1.stubs("accept").with(isA(Visitor.class)).will(dispatchItselfToVisitor);
		component2.stubs("accept").with(isA(Visitor.class)).will(dispatchItselfToVisitor);
		
		// expect...
		component1.expects("setExpectationIn").with(world);
		component2.expects("setExpectationIn").with(world).after(component1, "setExpectationIn");

		// when...
		outcomes.setExpectationIn(world);
	}

	public void shouldTellComponentsToVerifyWorld() throws Exception {
		// given...
		Mock component1 = mockComponent("component1");
		Mock component2 = mockComponent("component2");
		World world = (World)stub(World.class);
		Outcome outcomes = new Outcomes(new Outcome[] {(Outcome) component1, (Outcome) component2});
		
		// do what a real Outcome would do
		InvocationHandler dispatchItselfToVisitor = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitOutcome((Outcome)proxy);
				return null;
			}
		};
		component1.stubs("accept").with(isA(Visitor.class)).will(dispatchItselfToVisitor);
		component2.stubs("accept").with(isA(Visitor.class)).will(dispatchItselfToVisitor);
		
		// expect...
		component1.expects("verify").with(world);
		component2.expects("verify").with(world).after(component1, "verify");

		// when...
		outcomes.verify(world);
	}
}

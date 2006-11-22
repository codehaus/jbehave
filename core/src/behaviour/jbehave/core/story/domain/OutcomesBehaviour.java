package jbehave.core.story.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import jbehave.core.mock.Mock;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.World;
import jbehave.core.story.renderer.Renderer;

public class OutcomesBehaviour extends ScenarioComponentsBehaviour {

	protected ScenarioComponents newComposite(ScenarioComponent component1, ScenarioComponent component2) {
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
		InvocationHandler dispatchItselfToRenderer = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Renderer)args[0]).renderOutcome((Outcome)proxy);
				return null;
			}
		};
		component1.stubs("narrateTo").with(isA(Renderer.class)).will(dispatchItselfToRenderer);
		component2.stubs("narrateTo").with(isA(Renderer.class)).will(dispatchItselfToRenderer);
		
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
				((Renderer)args[0]).renderOutcome((Outcome)proxy);
				return null;
			}
		};
		component1.stubs("accept").with(isA(Renderer.class)).will(dispatchItselfToVisitor);
		component2.stubs("accept").with(isA(Renderer.class)).will(dispatchItselfToVisitor);
		
		// expect...
		component1.expects("verify").with(world);
		component2.expects("verify").with(world).after(component1, "verify");

		// when...
		outcomes.verify(world);
	}
}

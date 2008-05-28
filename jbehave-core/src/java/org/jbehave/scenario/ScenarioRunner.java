package org.jbehave.scenario;

import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;

public class ScenarioRunner {

	private class SomethingHappened extends State {
		@Override
		protected void run(Step step) {
			StepResult result = step.doNotPerform();
			result.describeTo(reporter);
		}

	}

	public Throwable throwable;

	private final class FineSoFar extends State {
		@Override
		protected void run(Step step) {
			StepResult result = step.perform();
			result.describeTo(reporter);
			if (!result.shouldContinue()) {
				state = new SomethingHappened();
				throwable = result.getThrowable();
			}
		}
	}

	private abstract class State {
		protected abstract void run(Step step);
	};
	
	private State state = new FineSoFar();
	private final ScenarioReporter reporter;

	public ScenarioRunner(ScenarioReporter reporter) {
		this.reporter = reporter;
	}

	public void run(Step... steps) throws Throwable {
		for (Step step : steps) {
			state.run(step);			
		}
		if (throwable != null) { throw throwable; }
	}

}

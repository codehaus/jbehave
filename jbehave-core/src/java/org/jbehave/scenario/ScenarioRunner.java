package org.jbehave.scenario;

import org.jbehave.scenario.steps.ErrorStrategy;
import org.jbehave.scenario.steps.PendingError;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;

/**
 * Runs the steps of a scenario keeping track of their state and describing the
 * results to the {@link ScenarioReporter}.
 * 
 * @author Elizabeth Keogh
 */
public class ScenarioRunner {

    private State state;
    private final ScenarioReporter reporter;
	private final PendingStepStrategy pendingStepStrategy;
	private ErrorStrategy errorStrategy;
	private ErrorStrategy currentStrategy;

    public ScenarioRunner(ScenarioReporter reporter, PendingStepStrategy pendingStepStrategy) {
    	this(reporter, pendingStepStrategy, ErrorStrategy.RETHROW);
    }
    
    public ScenarioRunner(ScenarioReporter reporter, PendingStepStrategy pendingStepStrategy, ErrorStrategy errorStrategy) {
        this.reporter = reporter;
		this.pendingStepStrategy = pendingStepStrategy;
		this.errorStrategy = errorStrategy;
		this.currentStrategy = ErrorStrategy.SILENT;
    }

    public void run(Step... steps) throws Throwable {
    	state = new FineSoFar();
        for (Step step : steps) {
            state.run(step);
        }
        currentStrategy.handleError(throwable);
    }

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
            throwable = result.getThrowable();
            if (throwable != null) {
                state = new SomethingHappened();
                currentStrategy = strategyFor(throwable);
            }
        }

		private ErrorStrategy strategyFor(Throwable throwable) {
			if (throwable instanceof PendingError) {
				return pendingStepStrategy;
			} else {
				return errorStrategy;
			}
		}
    }

    private abstract class State {
        protected abstract void run(Step step);
    };
}

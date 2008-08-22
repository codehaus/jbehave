package org.jbehave.scenario;

import org.jbehave.Configuration;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;
import org.jbehave.scenario.steps.Steps;

/**
 * Runs the steps of each scenario in a story
 * and describes the results to the {@link ScenarioReporter}.
 * 
 * @author Elizabeth Keogh
 */
public class ScenarioRunner {

    private State state = new FineSoFar();
	private ErrorStrategy currentStrategy;
	private PendingStepStrategy pendingStepStrategy;
	private ScenarioReporter reporter;
	private ErrorStrategy errorStrategy;
    private Throwable throwable;

	public ScenarioRunner() {
	}

	public void run(StoryDefinition story, Configuration configuration, Steps... candidateSteps) throws Throwable {
		reporter = configuration.forReportingScenarios();
		pendingStepStrategy = configuration.forPendingSteps();
		errorStrategy = configuration.forHandlingErrors();
		currentStrategy = ErrorStrategy.SILENT;
		throwable = null;
		
		reporter.beforeStory(story.getBlurb());
    	for (ScenarioDefinition scenario : story.getScenarios()) {
			Step[] steps = configuration.forCreatingSteps().createStepsFrom(scenario, candidateSteps);
	
			reporter.beforeScenario(scenario.getTitle());
	    	state = new FineSoFar();
	        for (Step step : steps) {
	            state.run(step);
	        }
	        reporter.afterScenario();
	        
    	}
    	reporter.afterStory();
    	currentStrategy.handleError(throwable);
	};

    private class SomethingHappened implements State {
        public void run(Step step) {
            StepResult result = step.doNotPerform();
            result.describeTo(reporter);
        }
    }

    private final class FineSoFar implements State {

        public void run(Step step) {
            StepResult result = step.perform();
            result.describeTo(reporter);
            Throwable thisScenariosThrowable = result.getThrowable();
            if (thisScenariosThrowable != null) {
                state = new SomethingHappened();
                throwable = mostImportantOf(throwable, thisScenariosThrowable);
                currentStrategy = strategyFor(throwable);
            }
        }

		private Throwable mostImportantOf(
				Throwable throwable1,
				Throwable throwable2) {
			return throwable1 == null ? throwable2 : 
				throwable1 instanceof PendingError ? (throwable2 == null ? throwable1 : throwable2) :
					throwable1;
		}

		private ErrorStrategy strategyFor(Throwable throwable) {
			if (throwable instanceof PendingError) {
				return pendingStepStrategy;
			} else {
				return errorStrategy;
			}
		}
    }

    private interface State {
        void run(Step step);
    }
}

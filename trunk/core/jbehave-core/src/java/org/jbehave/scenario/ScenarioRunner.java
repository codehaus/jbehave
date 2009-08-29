package org.jbehave.scenario;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.definition.Table;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;

/**
 * Runs the steps of each scenario in a story
 * and describes the results to the {@link ScenarioReporter}.
 * 
 * @author Elizabeth Keogh
 */
public class ScenarioRunner {

    private State state = new FineSoFar();
    private ErrorStrategy currentStrategy;
    private PendingErrorStrategy pendingStepStrategy;
    private ScenarioReporter reporter;
    private ErrorStrategy errorStrategy;
    private Throwable throwable;

    public ScenarioRunner() {
    }

    public void run(StoryDefinition story, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
        reporter = configuration.forReportingScenarios();
        pendingStepStrategy = configuration.forPendingSteps();
        errorStrategy = configuration.forHandlingErrors();
        currentStrategy = ErrorStrategy.SILENT;
        throwable = null;
        
        reporter.beforeStory(story.getBlurb());
        for (ScenarioDefinition scenario : story.getScenarios()) {
        	Table table = scenario.getTable();
        	if ( table != null && table.getRowCount() > 0 ){
        		for (Map<String,String> tableValues : table.getRows() ) {
					runScenario(configuration, scenario, tableValues, candidateSteps);
				}
        	} else {
            	runScenario(configuration, scenario, new HashMap<String, String>(), candidateSteps);        		
        	}
        }
        reporter.afterStory();
        currentStrategy.handleError(throwable);
    }

	private void runScenario(Configuration configuration,
			ScenarioDefinition scenario, Map<String, String> tableValues, CandidateSteps... candidateSteps) {
		Step[] steps = configuration.forCreatingSteps().createStepsFrom(scenario, tableValues, candidateSteps);
		reporter.beforeScenario(scenario.getTitle());
		state = new FineSoFar();
		for (Step step : steps) {
		    state.run(step);
		}
		reporter.afterScenario();
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

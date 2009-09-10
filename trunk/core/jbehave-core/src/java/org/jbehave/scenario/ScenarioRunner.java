package org.jbehave.scenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.definition.ExamplesTable;
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
 * @author Mauro Talevi
 * @author Paul Hammant
 */
public class ScenarioRunner {

    private State state = new FineSoFar();
    private ErrorStrategy currentStrategy;
    private PendingErrorStrategy pendingStepStrategy;
    private ScenarioReporter reporter;
    private ErrorStrategy errorStrategy;
    private Throwable throwable;

    public void run(Class<? extends RunnableScenario> scenarioClass, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
		StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(scenarioClass);
		run(story, configuration, candidateSteps);
    }

    public void run(String scenarioPath, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
		StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(scenarioPath);
		run(story, configuration, candidateSteps);
    }

    public void run(StoryDefinition story, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
        reporter = configuration.forReportingScenarios();
        pendingStepStrategy = configuration.forPendingSteps();
        errorStrategy = configuration.forHandlingErrors();
        currentStrategy = ErrorStrategy.SILENT;
        throwable = null;
        
        reporter.beforeStory(story.getBlurb());
        for (ScenarioDefinition scenario : story.getScenarios()) {
        	runGivenScenarios(configuration, scenario, candidateSteps); // first run any given scenarios, if any
        	if ( isTemplateScenario(scenario) ){ // run template scenario
        		runTemplateScenario(configuration, scenario, scenario.getTable(), candidateSteps);
        	} else { // run plain old scenario
            	runScenario(configuration, scenario, new HashMap<String, String>(), candidateSteps);        		
        	}
        }
        reporter.afterStory();
        currentStrategy.handleError(throwable);
    }

	private boolean isTemplateScenario(ScenarioDefinition scenario) {
		ExamplesTable table = scenario.getTable();
		return table != null && table.getRowCount() > 0;
	}

	private void runTemplateScenario(Configuration configuration,
			ScenarioDefinition scenario, ExamplesTable table,
			CandidateSteps... candidateSteps) {
		reporter.examplesTable(table);
		for (Map<String,String> tableRow : table.getRows() ) {
			reporter.examplesTableRow(tableRow);
			runScenario(configuration, scenario, tableRow, candidateSteps);
		}
	}

	private void runGivenScenarios(Configuration configuration,
			ScenarioDefinition scenario, CandidateSteps... candidateSteps)
			throws Throwable {
		List<String> givenScenarios = scenario.getGivenScenarios();
		if ( givenScenarios.size() > 0 ){
			reporter.givenScenarios(givenScenarios);
			for ( String scenarioPath : givenScenarios ){
				run(scenarioPath, configuration, candidateSteps);
			}
		}
	}

	private void runScenario(Configuration configuration,
			ScenarioDefinition scenario, Map<String, String> tableRow, CandidateSteps... candidateSteps) {
		Step[] steps = configuration.forCreatingSteps().createStepsFrom(scenario, tableRow, candidateSteps);
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

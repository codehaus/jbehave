package org.jbehave.scenario;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.StepResult;

/**
 * Runs the steps of each scenario in a story and describes the results to the {@link ScenarioReporter}.
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
    private StepCreator stepCreator;

    public void run(Class<? extends RunnableScenario> scenarioClass, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
		StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(scenarioClass);
		story.namedAs(scenarioClass.getSimpleName());
		run(story, configuration, candidateSteps);
    }

    public void run(String scenarioPath, Configuration configuration, boolean embeddedStory, CandidateSteps... candidateSteps) throws Throwable {
		StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(scenarioPath);
        story.namedAs(new File(scenarioPath).getName());
		run(story, configuration, embeddedStory, candidateSteps);
    }    

    public void run(StoryDefinition story, Configuration configuration, CandidateSteps... candidateSteps) throws Throwable {
        run(story, configuration, false, candidateSteps);
    }
    
    public void run(StoryDefinition story, Configuration configuration, boolean embeddedStory, CandidateSteps... candidateSteps) throws Throwable {
        stepCreator = configuration.forCreatingSteps();
        reporter = configuration.forReportingScenarios();
        pendingStepStrategy = configuration.forPendingSteps();
        errorStrategy = configuration.forHandlingErrors();
        currentStrategy = ErrorStrategy.SILENT;
        throwable = null;
        
        reporter.beforeStory(story, embeddedStory);          
        runSteps(stepCreator.createStepsFrom(story, StepCreator.Stage.BEFORE, embeddedStory, candidateSteps), embeddedStory);
        for (ScenarioDefinition scenario : story.getScenarios()) {
    		reporter.beforeScenario(scenario.getTitle());
        	runGivenScenarios(configuration, scenario, candidateSteps); // first run any given scenarios, if any
        	if ( isExamplesTableScenario(scenario) ){ // run examples table scenario
        		runExamplesTableScenario(configuration, scenario, candidateSteps);
        	} else { // run plain old scenario
            	runScenario(configuration, scenario, new HashMap<String, String>(), candidateSteps);        		
        	}
    		reporter.afterScenario();
        }
        runSteps(stepCreator.createStepsFrom(story, StepCreator.Stage.AFTER, embeddedStory, candidateSteps), embeddedStory);
        reporter.afterStory(embeddedStory);            
        currentStrategy.handleError(throwable);
    }

	
    /**
     * Runs a list of steps.  The running can be skipped in certain cases,
     * e.g. when running in embedded story mode.
     * 
     * @param steps the Steps to run
     * @param skip the boolean flag to skip running
     */
    private void runSteps(Step[] steps, boolean skip) {
        if ( skip ) return; 
        state = new FineSoFar();
        for (Step step : steps) {
            state.run(step);
        }
    }
	
    private void runGivenScenarios(Configuration configuration,
			ScenarioDefinition scenario, CandidateSteps... candidateSteps)
			throws Throwable {
		List<String> givenScenarios = scenario.getGivenScenarios();
		if ( givenScenarios.size() > 0 ){
			reporter.givenScenarios(givenScenarios);
			for ( String scenarioPath : givenScenarios ){
			    // run in embedded mode
				run(scenarioPath, configuration, true, candidateSteps);
			}
		}
	}

	private boolean isExamplesTableScenario(ScenarioDefinition scenario) {
		ExamplesTable table = scenario.getTable();
		return table != null && table.getRowCount() > 0;
	}

	private void runExamplesTableScenario(Configuration configuration,
			ScenarioDefinition scenario, CandidateSteps... candidateSteps) {
		ExamplesTable table = scenario.getTable();
        reporter.beforeExamples(scenario.getSteps(), table);
		for (Map<String,String> tableRow : table.getRows() ) {
			reporter.example(tableRow);
			runScenario(configuration, scenario, tableRow, candidateSteps);
		}
		reporter.afterExamples();
	}

	private void runScenario(Configuration configuration,
			ScenarioDefinition scenario, Map<String, String> tableRow, CandidateSteps... candidateSteps) {
        Step[] steps = stepCreator.createStepsFrom(scenario, tableRow, candidateSteps);
		runSteps(steps, false);
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

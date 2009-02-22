package org.jbehave.scenario;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.reporters.StepdocReporter;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.StepdocGenerator;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;


/**
 * The example code bases provide the only examples of the whole of JBehave running.
 */
public class ScenarioBehaviour {

    @SuppressWarnings("unchecked")
    @Test
    public void shouldLoadStoryDefinitionAndRunUsingTheScenarioRunner() throws Throwable {
        ScenarioRunner runner = mock(ScenarioRunner.class);
        MockedConfiguration configuration = new MockedConfiguration();
        CandidateSteps steps = mock(Steps.class);
        RunnableScenario scenario = new MyScenario(runner, configuration, steps);
        
        StoryDefinition storyDefinition = new StoryDefinition(Blurb.EMPTY, Collections.EMPTY_LIST);
        stub(configuration.scenarioDefiner.loadScenarioDefinitionsFor(MyScenario.class)).toReturn(storyDefinition);
        
        scenario.runScenario();
        
        verify(runner).run(storyDefinition, configuration, steps);
    }

    private class MyScenario extends JUnitScenario {

        public MyScenario(ScenarioRunner runner,
                MockedConfiguration configuration, CandidateSteps steps) {
            super(runner, configuration, steps);
        }

    }

    private static class MockedConfiguration implements Configuration {

        private ScenarioDefiner scenarioDefiner = mock(ScenarioDefiner.class);

        public StepCreator forCreatingSteps() { return null; }

        public ScenarioDefiner forDefiningScenarios() { return scenarioDefiner; }

        public ErrorStrategy forHandlingErrors() { return null; }

        public PendingErrorStrategy forPendingSteps() { return null; }

        public ScenarioReporter forReportingScenarios() { return null; }

        public KeyWords keywords() { return null; }

		public StepdocGenerator forGeneratingStepdoc() { return null; }

		public StepdocReporter forReportingStepdoc() { return null; }
        
    }
}

package org.jbehave.scenario;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.steps.CandidateSteps;
import org.junit.Test;

public class ScenarioBehaviour {

    @Test
    public void shouldLoadStoryDefinitionAndRunUsingTheScenarioRunner() throws Throwable {
        // Given
        ScenarioRunner runner = mock(ScenarioRunner.class);
        ScenarioDefiner scenarioDefiner = mock(ScenarioDefiner.class);
        Configuration configuration = mock(Configuration.class);
        CandidateSteps steps = mock(CandidateSteps.class);
        StoryDefinition storyDefinition = new StoryDefinition();
        stub(configuration.forDefiningScenarios()).toReturn(scenarioDefiner);
        stub(scenarioDefiner.loadScenarioDefinitionsFor(MyScenario.class)).toReturn(storyDefinition);

        // When
        RunnableScenario scenario = new MyScenario(runner, configuration, steps);
        scenario.runScenario();

        // Then
        verify(runner).run(storyDefinition, configuration, false, steps);
    }

    private class MyScenario extends JUnitScenario {

        public MyScenario(ScenarioRunner runner, Configuration configuration, CandidateSteps steps) {
            super(runner, configuration, steps);
        }

    }

}

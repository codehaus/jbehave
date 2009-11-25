package org.jbehave.scenario;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jbehave.scenario.steps.CandidateSteps;
import org.junit.Test;

public class ScenarioBehaviour {

    @Test
    public void shouldRunUsingTheScenarioRunner() throws Throwable {
        // Given
        ScenarioRunner runner = mock(ScenarioRunner.class);
        Configuration configuration = mock(Configuration.class);
        CandidateSteps steps = mock(CandidateSteps.class);
        Class<MyScenario> scenarioClass = MyScenario.class;

        // When
        RunnableScenario scenario = new MyScenario(runner, configuration, steps);
        scenario.runScenario();

        // Then
        verify(runner).run(scenarioClass, configuration, steps);
    }

    private class MyScenario extends JUnitScenario {

        public MyScenario(ScenarioRunner runner, Configuration configuration, CandidateSteps steps) {
            super(runner, configuration, steps);
        }

    }

}

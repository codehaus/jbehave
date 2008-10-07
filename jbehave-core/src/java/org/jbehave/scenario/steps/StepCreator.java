package org.jbehave.scenario.steps;

import org.jbehave.scenario.definition.ScenarioDefinition;

public interface StepCreator {

    Step[] createStepsFrom(ScenarioDefinition scenario, Steps... candidateSteps);

}

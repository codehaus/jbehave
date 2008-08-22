package org.jbehave.scenario.steps;

import org.jbehave.scenario.ScenarioDefinition;

public interface StepCreator {

	Step[] createStepsFrom(ScenarioDefinition scenario, Steps... candidateSteps);

}

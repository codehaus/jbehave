package org.jbehave.scenario.steps;

import java.util.Map;

import org.jbehave.scenario.definition.ScenarioDefinition;

/**
 * Represents the strategy for the creation of executable {@link Step}s from a
 * given scenario definition matching a list of {@link CandidateSteps}.
 */
public interface StepCreator {

	Step[] createStepsFrom(ScenarioDefinition scenarioDefinition,
			Map<String, String> tableRow, CandidateSteps... candidateSteps);

}

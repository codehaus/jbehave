package org.jbehave.scenario.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.ScenarioDefinition;

/**
 * StepCreator that marks unmatched steps as {@link StepResult.Pending}
 */
public class UnmatchedToPendingStepCreator implements StepCreator {

	public Step[] createStepsFrom(ScenarioDefinition scenario,
			Map<String, String> tableRow,
			CandidateSteps... candidateSteps) {
		List<Step> steps = new ArrayList<Step>();

		addAllNormalSteps(scenario, steps, tableRow, candidateSteps);
		addBeforeAndAfterSteps(steps, candidateSteps);

		return steps.toArray(new Step[steps.size()]);
	}

	private void addBeforeAndAfterSteps(List<Step> steps,
			CandidateSteps[] candidateSteps) {
		for (CandidateSteps candidates : candidateSteps) {
			steps.addAll(0, candidates.runBeforeScenario());
		}

		for (CandidateSteps candidates : candidateSteps) {
			steps.addAll(candidates.runAfterScenario());
		}
	}

	private void addAllNormalSteps(ScenarioDefinition scenarioDefinition,
			List<Step> steps, Map<String, String> tableRow, CandidateSteps... candidateSteps) {
		for (String stringStep : scenarioDefinition.getSteps()) {
			Step step = new PendingStep(stringStep);
			for (CandidateSteps candidates : candidateSteps) {
				for (CandidateStep candidate : candidates.getSteps()) {
					if (candidate.matches(stringStep)) {
						step = candidate.createFrom(tableRow, stringStep);
						break;
					}
				}
			}
			steps.add(step);
		}
	}
}

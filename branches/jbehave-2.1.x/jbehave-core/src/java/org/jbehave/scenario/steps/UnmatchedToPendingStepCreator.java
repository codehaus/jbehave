package org.jbehave.scenario.steps;

import java.util.ArrayList;

import org.jbehave.scenario.definition.ScenarioDefinition;

public class UnmatchedToPendingStepCreator implements StepCreator {

    public Step[] createStepsFrom(ScenarioDefinition scenario,
            CandidateSteps... candidateSteps) {
        ArrayList<Step> steps = new ArrayList<Step>();
        
        addAllNormalSteps(scenario, steps, candidateSteps);
        addBeforeAndAfterSteps(steps, candidateSteps);
        
        return steps.toArray(new Step[steps.size()]);
    }

	private void addBeforeAndAfterSteps(ArrayList<Step> steps,
			CandidateSteps[] candidateSteps) {
		for (CandidateSteps candidates : candidateSteps) {
			steps.addAll(0, candidates.runBeforeScenario());
		}
		
		for (CandidateSteps candidates : candidateSteps) {
			steps.addAll(candidates.runAfterScenario());
		}
	}

	private void addAllNormalSteps(ScenarioDefinition scenario,
			ArrayList<Step> steps, CandidateSteps... candidateSteps) {
		for (String stringStep : scenario.getSteps()) {
			Step step = new PendingStep(stringStep);
            for (CandidateSteps candidates : candidateSteps) {
                for (CandidateStep candidate : candidates.getSteps()) {
                    if (candidate.matches(stringStep)) {
                        step = candidate.createFrom(stringStep);
                        break;
                    }
                }
            }
            steps.add(step);
        }
	}
}

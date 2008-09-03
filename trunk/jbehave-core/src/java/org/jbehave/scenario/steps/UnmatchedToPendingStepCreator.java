package org.jbehave.scenario.steps;

import org.jbehave.scenario.definition.ScenarioDefinition;

public class UnmatchedToPendingStepCreator implements StepCreator {

    public Step[] createStepsFrom(ScenarioDefinition scenario,
            Steps... candidateSteps) {
        Step[] steps = new Step[scenario.getSteps().size()];
        for (int i = 0; i < steps.length; i++) {
            String stringStep = scenario.getSteps().get(i);
            for (Steps candidates : candidateSteps) {
                for (CandidateStep candidate : candidates.getSteps()) {
                    if (candidate.matches(stringStep)) {
                        steps[i] = candidate.createFrom(stringStep);
                    }
                }
            }
            if (steps[i] == null) {
                steps[i] = new PendingStep(stringStep);
            }
        }
        return steps;
    }
}

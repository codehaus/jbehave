package org.jbehave.scenario.steps;

public class StepsFactory {

    public static CandidateSteps[] makeCandidateSteps(Object... stepsInstances) {
        return makeCandidateSteps(new StepsConfiguration(), stepsInstances);
    }

    public static CandidateSteps[] makeCandidateSteps(StepsConfiguration configuration, Object... stepsInstances) {
        CandidateSteps[] candidateSteps = new CandidateSteps[stepsInstances.length];
        for (int i = 0; i < stepsInstances.length; i++) {
            Object instance = stepsInstances[i];
            candidateSteps[i] = Steps.make(configuration, instance);
        }
        return candidateSteps;
    }
}

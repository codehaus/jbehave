package org.jbehave.scenario.steps.pico;

import java.lang.reflect.Method;

import org.jbehave.scenario.steps.CandidateStep;
import org.jbehave.scenario.steps.StepType;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.picocontainer.PicoContainer;

public class PicoEnabledSteps extends Steps {

    public PicoEnabledSteps(PicoEnabledStepsConfiguration configuration) {
        super(configuration);
    }

    protected CandidateStep createCandidateStep(Method method, StepType stepType, String stepPatternAsString, StepsConfiguration configuration) {
        if (!(configuration instanceof PicoEnabledStepsConfiguration)) {
            throw new PicoEnabledException("StepsConfiguration parameter should be instanceof PicoEnabledStepsConfiguration");
        }
        PicoContainer parent = ((PicoEnabledStepsConfiguration) configuration).getParentContainer();
        return new PicoEnabledCandidateStep(stepPatternAsString, stepType, method,
                this, configuration.getPatternBuilder(),
                configuration.getParameterConverters(),
                configuration.getStartingWordsByType(), parent);
    }

}

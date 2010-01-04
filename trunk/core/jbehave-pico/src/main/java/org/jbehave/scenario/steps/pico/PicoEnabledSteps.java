package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.steps.*;
import org.picocontainer.PicoContainer;

import java.lang.reflect.Method;

public class PicoEnabledSteps extends Steps {

    public PicoEnabledSteps(PicoEnabledStepsConfiguration configuration) {
        super(configuration);
    }

    protected CandidateStep createCandidateStep(Method method, StepType stepType, String stepPatternAsString, StepsConfiguration configuration) {
        if (!(configuration instanceof PicoEnabledStepsConfiguration)) {
            throw new PicoEnabledException("StepsConfiguration parameter should be instanceof PicoEnabledStepsConfiguration");
        }
        return new PicoEnabledCandidateStep(stepPatternAsString, stepType, method,
                this, configuration.getPatternBuilder(),
                configuration.getParameterConverters(),
                configuration.getStartingWordsByType(), ((PicoEnabledStepsConfiguration) configuration).getParentContainer());
    }


}

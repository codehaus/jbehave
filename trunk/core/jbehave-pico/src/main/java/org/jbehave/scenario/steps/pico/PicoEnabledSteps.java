package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.steps.*;
import org.picocontainer.PicoContainer;

import java.lang.reflect.Method;

public class PicoEnabledSteps extends Steps {

    private PicoContainer parent;

    public PicoEnabledSteps() {
        super();
    }

    public PicoEnabledSteps(KeyWords keywords) {
        super(keywords);
    }

    public PicoEnabledSteps(ParameterConverters converters) {
        super(converters);
    }

    public PicoEnabledSteps(StepsConfiguration configuration) {
        super(configuration);
    }

    public PicoEnabledSteps withParentContainer(PicoContainer parent) {
        this.parent = parent;
        return this;
    }

    protected CandidateStep createCandidateStep(Method method, StepType stepType, String stepPatternAsString, StepsConfiguration configuration) {
        if (parent == null) {
            throw new NullPointerException("Parent container should be provided via PicoEnabledSteps#withParentContainer(PicoContainer)");
        }
        return new PicoEnabledCandidateStep(stepPatternAsString, stepType, method,
                this, configuration.getPatternBuilder(),
                configuration.getParameterConverters(),
                configuration.getStartingWordsByType(), parent);
    }


}

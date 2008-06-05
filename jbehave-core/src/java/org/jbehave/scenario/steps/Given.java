package org.jbehave.scenario.steps;

import java.lang.reflect.Method;

import org.jbehave.scenario.CandidateStep;

public class Given extends CandidateStep {

    public Given(String matchThis, Method method, Steps steps, StepPatternBuilder patternBuilder) {
        super(matchThis, method, steps, patternBuilder);
    }

    @Override
    protected String precursor() {
        return "Given ";
    }

}

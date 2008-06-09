package org.jbehave.scenario.steps;

import java.lang.reflect.Method;


public class When extends CandidateStep {

	public When(String value, Method method, Steps steps, StepPatternBuilder patternBuilder) {
		super(value, method, steps, patternBuilder);
	}

	@Override
	protected String precursor() {
		return "When ";
	}

}

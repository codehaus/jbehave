package org.jbehave.scenario.steps;

import java.lang.reflect.Method;


public class When extends CandidateStep {

	public When(String value, Method method, Steps steps, StepPatternBuilder patternBuilder, StepMonitor monitor) {
		super(value, method, steps, patternBuilder, monitor);
	}

	@Override
	protected String precursor() {
		return "When ";
	}

}

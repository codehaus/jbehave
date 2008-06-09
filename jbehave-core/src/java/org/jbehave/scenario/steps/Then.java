package org.jbehave.scenario.steps;

import java.lang.reflect.Method;


public class Then extends CandidateStep {

	public Then(String matchThis, Method method, Steps steps, StepPatternBuilder patternBuilder) {
		super(matchThis, method, steps, patternBuilder);
	}

	@Override
	protected String precursor() {
		return "Then ";
	}

}

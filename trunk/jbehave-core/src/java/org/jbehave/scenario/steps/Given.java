package org.jbehave.scenario.steps;

import java.lang.reflect.Method;

import org.jbehave.scenario.CandidateStep;

public class Given extends CandidateStep {


	public Given(String matchThis, Method method, Steps steps, StepRegexpBuilder argToRegExp) {
		super(matchThis, method, steps, argToRegExp);
	}

	@Override
	protected String precursor() {
		return "Given ";
	}

}

package org.jbehave.scenario.steps;

import java.lang.reflect.Method;

import org.jbehave.scenario.CandidateStep;

public class When extends CandidateStep {

	public When(String value, Method method, Steps steps, StepRegexpBuilder argToRegExp) {
		super(value, method, steps, argToRegExp);
	}

	@Override
	protected String precursor() {
		return "When ";
	}

}

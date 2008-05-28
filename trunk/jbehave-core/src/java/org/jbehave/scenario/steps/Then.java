package org.jbehave.scenario.steps;

import java.lang.reflect.Method;

import org.jbehave.scenario.CandidateStep;

public class Then extends CandidateStep {

	public Then(String matchThis, Method method, Steps steps, StepRegexpBuilder argToRegExp) {
		super(matchThis, method, steps, argToRegExp);
	}

	@Override
	protected String precursor() {
		return "Then ";
	}

}

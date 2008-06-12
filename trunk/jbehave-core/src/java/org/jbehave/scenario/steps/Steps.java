package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Steps {
	
	private final StepPatternBuilder patternBuilder;
	
	public Steps() {
		this(new DollarStepPatternBuilder());
	}

	public Steps(StepPatternBuilder patternConverter) {
		this.patternBuilder = patternConverter;
	}

	/**
	 * Returns all the steps that can be performed by this class.
	 */
	public CandidateStep[] getSteps() {
		ArrayList<CandidateStep> steps = new ArrayList<CandidateStep>();
		for (Method method : this.getClass().getMethods()) {
			if (method.isAnnotationPresent(org.jbehave.scenario.annotations.Given.class)) {
				steps.add(given(method.getAnnotation(org.jbehave.scenario.annotations.Given.class).value(), method));
			}
			if (method.isAnnotationPresent(org.jbehave.scenario.annotations.When.class)) {
				steps.add(when(method.getAnnotation(org.jbehave.scenario.annotations.When.class).value(), method));
			}
			if (method.isAnnotationPresent(org.jbehave.scenario.annotations.Then.class)) {
				steps.add(then(method.getAnnotation(org.jbehave.scenario.annotations.Then.class).value(), method));
			}
		}
		return steps.toArray(new CandidateStep[steps.size()]);
	}
	
	private Given given(String value, Method method) {
		return new Given(value, method, this, patternBuilder);
	}
	private When when(String value, Method method) {
		return new When(value, method, this, patternBuilder);
	}
	private Then then(String value, Method method) {
		return new Then(value, method, this, patternBuilder);
	}
}

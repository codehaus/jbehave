package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

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
			if (method.isAnnotationPresent(Given.class)) {
				steps.add(given(method.getAnnotation(Given.class).value(), method));
			}
			if (method.isAnnotationPresent(When.class)) {
				steps.add(when(method.getAnnotation(When.class).value(), method));
			}
			if (method.isAnnotationPresent(Then.class)) {
				steps.add(then(method.getAnnotation(Then.class).value(), method));
			}
		}
		return steps.toArray(new CandidateStep[steps.size()]);
	}
	
	private org.jbehave.scenario.steps.Given given(String value, Method method) {
		return new org.jbehave.scenario.steps.Given(value, method, this, patternBuilder);
	}
	private org.jbehave.scenario.steps.When when(String value, Method method) {
		return new org.jbehave.scenario.steps.When(value, method, this, patternBuilder);
	}
	private org.jbehave.scenario.steps.Then then(String value, Method method) {
		return new org.jbehave.scenario.steps.Then(value, method, this, patternBuilder);
	}
}

package org.jbehave.scenario.errors;

import static java.text.MessageFormat.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.BeforeScenario;

/**
 * Thrown when methods annotated with {@link BeforeScenario @BeforeScenario} or
 * {@link AfterScenario @AfterScenario} fail.
 */
@SuppressWarnings("serial")
public class BeforeOrAfterScenarioException extends RuntimeException {

	private static final String MESSAGE = "Method {0}.{1}, annotated with {2} failed.";

	public BeforeOrAfterScenarioException(
			Class<? extends Annotation> annotation, Method method, Throwable t) {
		super(format(MESSAGE, method.getClass().getSimpleName(),
				method.getName(), annotation.getSimpleName()), t);
	}
}

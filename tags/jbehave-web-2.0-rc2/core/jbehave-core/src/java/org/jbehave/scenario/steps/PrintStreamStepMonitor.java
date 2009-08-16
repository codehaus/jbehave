package org.jbehave.scenario.steps;

import static java.text.MessageFormat.format;

import java.io.PrintStream;
import java.lang.reflect.Type;

/**
 * StepMonitor that prints to a {@link PrintStream}, defaulting to
 * {@link System.out}.
 */
public class PrintStreamStepMonitor implements StepMonitor {
	private static final String CONVERTED_VALUE_OF_TYPE = "Converted value ''{0}'' of type ''{1}'' to ''{2}'' with converter ''{3}''";
	private static final String STEP_MATCHES_PATTERN = "Step ''{0}'' {1} pattern ''{2}''";
	private static final String MATCHES = "matches";
	private static final String DOES_NOT_MATCH = "does not match";
	private final PrintStream output;

	public PrintStreamStepMonitor() {
		this(System.out);
	}

	public PrintStreamStepMonitor(PrintStream output) {
		this.output = output;
	}

	public void stepMatchesPattern(String step, boolean matches, String pattern) {
		String message = format(STEP_MATCHES_PATTERN, step, (matches ? MATCHES
				: DOES_NOT_MATCH), pattern);
		print(output, message);
	}

	public void convertedValueOfType(String value, Type type, Object converted,
			Class<?> converterClass) {
		String message = format(CONVERTED_VALUE_OF_TYPE, value, type,
				converted, converterClass);
		print(output, message);
	}

	public void performing(String step) {
		print(output, step);
	}

	protected void print(PrintStream output, String message) {
		output.println(message);
	}

}

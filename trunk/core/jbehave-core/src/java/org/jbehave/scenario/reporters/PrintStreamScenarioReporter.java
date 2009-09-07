package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 */
public class PrintStreamScenarioReporter implements ScenarioReporter {

	private final PrintStream output;
	private final boolean reportErrors;
	private Throwable cause;

	public PrintStreamScenarioReporter() {
		this(System.out);
	}

	public PrintStreamScenarioReporter(PrintStream output) {
		this(output, false);
	}

	public PrintStreamScenarioReporter(PrintStream output, boolean reportErrors) {
		this.output = output;
		this.reportErrors = reportErrors;
	}

	public void successful(String step) {
		output.println(format("successful", "{0}", step));
	}

	public void pending(String step) {
		output.println(format("pending", "{0} (PENDING)", step));
	}

	public void notPerformed(String step) {
		output.println(format("notPerformed", "{0} (NOT PERFORMED)", step));
	}

	public void failed(String step, Throwable cause) {
		this.cause = cause;
		output.println(format("failed", "{0} (FAILED)", step));
	}

	public void afterScenario() {
		output.println();
		if (reportErrors && cause != null) {
			cause.printStackTrace(output);
			output.println();
		}
	}

	public void beforeScenario(String title) {
		cause = null;
		output.println(format("beforeScenario", "Scenario: {0}\n", title));
	}

	public void afterStory() {
		output.println(format("afterStory", "" ));
	}

	public void beforeStory(Blurb blurb) {
		output.println(format("beforeStory", "{0}", blurb.asString()));
	}

	public void givenScenarios(List<String> givenScenarios) {
		output.println(format("givenScenarios", "GivenScenarios: {0}\n",
				givenScenarios));
	}

	public void usingExamplesTable(ExamplesTable table) {
		output.println(format("usingExamplesTable",
				"Using examples table:\n\n{0}\n\n", table));
	}

	public void usingTableRow(Map<String, String> tableRow) {
		output.println(format("usingTableRow", "Using table row: {0}\n", tableRow));
	}

	protected String format(String key, String defaultPattern, Object... args) {
		return MessageFormat.format(patternFor(key, defaultPattern), args);
	}

	/**
	 * Provide format patterns for the output by key, conventionally equal to the method name.
	 * If no pattern is found for key or needs to be overridden, the default pattern should be returned. 
	 * 
	 * @param key the format pattern key
	 * @param defaultPattern the default pattern if no pattern is 
	 * @return The format patter for the given key
	 */
	protected String patternFor(String key, String defaultPattern) {
		return defaultPattern;
	}

}

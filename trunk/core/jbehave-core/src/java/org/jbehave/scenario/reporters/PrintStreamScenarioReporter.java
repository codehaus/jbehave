package org.jbehave.scenario.reporters;

import java.io.PrintStream;
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
		output.println(step);
	}

	public void pending(String step) {
		output.println(step + " (PENDING)");
	}

	public void notPerformed(String step) {
		output.println(step + " (NOT PERFORMED)");
	}

	public void failed(String step, Throwable cause) {
		this.cause = cause;
		output.println(step + " (FAILED)");
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
		output.println("Scenario: " + title);
		output.println();
	}

	public void afterStory() {

	}

	public void beforeStory(Blurb blurb) {
		output.println(blurb.asString());
		output.println();
	}

	public void givenScenarios(List<String> givenScenarios) {
		output.println("GivenScenarios: " + givenScenarios);
	}

	public void usingExamplesTable(ExamplesTable table) {
		output.println("Using examples table:");
		output.println();
		output.println(table);
		output.println();
	}

	public void usingTableRow(Map<String, String> tableRow) {
		output.println("Using table row: " + tableRow);
		output.println();
	}

}

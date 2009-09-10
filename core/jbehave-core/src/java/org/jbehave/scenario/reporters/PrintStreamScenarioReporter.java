package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.i18n.I18nKeyWords;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 */
public class PrintStreamScenarioReporter implements ScenarioReporter {

	private final PrintStream output;
	private final KeyWords keywords;
	private final boolean reportErrors;
	private Throwable cause;

	public PrintStreamScenarioReporter() {
		this(System.out);
	}

	public PrintStreamScenarioReporter(PrintStream output) {
		this(output, new I18nKeyWords(), false);
	}

	public PrintStreamScenarioReporter(PrintStream output, KeyWords keywords, boolean reportErrors) {
		this.output = output;
		this.keywords = keywords;
		this.reportErrors = reportErrors;
	}

	public void successful(String step) {
		output.println(format("successful", "{0}", step));
	}

	public void pending(String step) {
		output.println(format("pending", "{0} ({1})", step, keywords.pending()));
	}

	public void notPerformed(String step) {
		output.println(format("notPerformed", "{0} ({1})", step, keywords.notPerformed()));
	}

	public void failed(String step, Throwable cause) {
		this.cause = cause;
		output.println(format("failed", "{0} ({1})", step, keywords.failed()));
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
		output.println(format("beforeScenario", "{0} {1}\n", keywords.scenario(), title));		
	}

	public void afterStory() {
		output.println(format("afterStory", "" ));
	}

	public void beforeStory(Blurb blurb) {
		output.println(format("beforeStory", "{0}", blurb.asString()));
	}

	public void givenScenarios(List<String> givenScenarios) {
		output.println(format("givenScenarios", "{0} {1}\n",
				keywords.givenScenarios(), givenScenarios));
	}

	public void examplesTable(ExamplesTable table) {
		output.println(format("examplesTable",
				"{0}\n\n{1}\n\n", keywords.examplesTable(), table));
	}

	public void examplesTableRow(Map<String, String> tableRow) {
		output.println(format("tableRow", "{0} {1}\n", keywords.examplesTableRow(), tableRow));
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

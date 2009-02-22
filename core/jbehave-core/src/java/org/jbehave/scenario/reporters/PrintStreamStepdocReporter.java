package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.util.List;

import org.jbehave.scenario.steps.Stepdoc;

/**
 * <p>
 * Stepdoc reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 */
public class PrintStreamStepdocReporter implements StepdocReporter {

	private final PrintStream output;
	private final boolean reportMethods;

	public PrintStreamStepdocReporter() {
		this(System.out);
	}

	public PrintStreamStepdocReporter(PrintStream output) {
		this(output, false);
	}

	public PrintStreamStepdocReporter(PrintStream output, boolean reportMethods) {
		this.output = output;
		this.reportMethods = reportMethods;
	}

	public void report(List<Stepdoc> stepdocs) {
		for (Stepdoc stepdoc : stepdocs) {
			output.println(stepdoc.getAnnotation().getSimpleName() + " "
					+ stepdoc.getPattern());
			if (stepdoc.getAliasPatterns().size() > 0) {
				output.println("Aliases: " + stepdoc.getAliasPatterns());
			}
			if (reportMethods) {
				output.println("Method: " + stepdoc.getMethod());
			}
		}
	}

}

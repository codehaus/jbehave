package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.util.List;

import org.jbehave.scenario.steps.Stepdoc2;

/**
 * <p>
 * Stepdoc2 reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 */
public class PrintStreamStepdoc2Reporter implements Stepdoc2Reporter {

	private final PrintStream output;
	private final boolean reportMethods;

	public PrintStreamStepdoc2Reporter() {
		this(System.out);
	}

	public PrintStreamStepdoc2Reporter(PrintStream output) {
		this(output, false);
	}

	public PrintStreamStepdoc2Reporter(PrintStream output, boolean reportMethods) {
		this.output = output;
		this.reportMethods = reportMethods;
	}

	public void report(List<Stepdoc2> stepdocs) {
		for (Stepdoc2 stepdoc : stepdocs) {
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

package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.util.List;

import org.jbehave.scenario.steps.StepDoc;

/**
 * <p>
 * StepDoc reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 */
public class PrintStreamStepDocReporter implements StepDocReporter {

	private final PrintStream output;
	private final boolean reportMethods;

	public PrintStreamStepDocReporter() {
		this(System.out);
	}

	public PrintStreamStepDocReporter(PrintStream output) {
		this(output, false);
	}

	public PrintStreamStepDocReporter(PrintStream output, boolean reportMethods) {
		this.output = output;
		this.reportMethods = reportMethods;
	}

	public void report(List<StepDoc> stepdocs) {
		for (StepDoc stepdoc : stepdocs) {
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

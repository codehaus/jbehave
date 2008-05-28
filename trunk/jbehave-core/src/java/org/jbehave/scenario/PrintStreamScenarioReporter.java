package org.jbehave.scenario;

import java.io.PrintStream;

public class PrintStreamScenarioReporter implements ScenarioReporter {

	private final PrintStream output;
	
	public PrintStreamScenarioReporter(PrintStream output) {
		this.output = output;
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

	public void failed(String step, Throwable e) {
		output.println(step + " (FAILED)");
	}

}

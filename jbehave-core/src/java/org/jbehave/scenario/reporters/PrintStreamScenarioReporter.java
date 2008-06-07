package org.jbehave.scenario.reporters;

import java.io.PrintStream;

import org.jbehave.scenario.ScenarioReporter;

public class PrintStreamScenarioReporter implements ScenarioReporter {

	private final PrintStream output;
	
	public PrintStreamScenarioReporter(){
	    this(System.out);
    }
	
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

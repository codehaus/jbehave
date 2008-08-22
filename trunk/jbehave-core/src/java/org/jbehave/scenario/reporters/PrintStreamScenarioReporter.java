package org.jbehave.scenario.reporters;

import java.io.PrintStream;

import org.jbehave.scenario.ScenarioReporter;
import org.jbehave.scenario.definition.Blurb;

public class PrintStreamScenarioReporter implements ScenarioReporter {

	private final PrintStream output;
	private Throwable e;
	private final boolean reportErrors;
	
	public PrintStreamScenarioReporter(){
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

	public void failed(String step, Throwable e) {
		this.e = e;
		output.println(step + " (FAILED)");
	}

	public void afterScenario() {
		output.println();
		if (reportErrors && e != null) {
			e.printStackTrace(output);
			output.println();
		}
	}

	public void beforeScenario(String title) {
		e = null;
		output.println("Scenario: " + title);
		output.println();
	}

	public void afterStory() {
		
	}

	public void beforeStory(Blurb blurb) {
		output.println(blurb.asString());
		output.println();
	}

}

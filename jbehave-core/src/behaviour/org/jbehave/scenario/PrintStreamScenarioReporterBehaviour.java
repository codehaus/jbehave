package org.jbehave.scenario;

import static org.jbehave.Ensure.ensureThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;


public class PrintStreamScenarioReporterBehaviour {
	
	private static final String NL = System.getProperty("line.separator");

	@Test
	public void shouldOutputStepsAndResultToPrintStream() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out));
		reporter.successful("Given I have a balance of $50");
		reporter.successful("When I request $20");
		reporter.failed("When I ask Liz for a loan of $100", new IllegalAccessException("Leave my money alone!"));
		reporter.pending("Then I should have a balance of $30");
		reporter.notPerformed("Then I should have $20");
		
		ensureThat(out.toString(), equalTo(
				"Given I have a balance of $50" + NL +
				"When I request $20" + NL +
				"When I ask Liz for a loan of $100 (FAILED)" + NL +
				"Then I should have a balance of $30 (PENDING)" + NL +
				"Then I should have $20 (NOT PERFORMED)" + NL));
	}


}

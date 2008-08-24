package org.jbehave.scenario.reporters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

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
		reporter.successful("When I ask Liz for a loan of $100");
		reporter.pending("Then I should have a balance of $30");
		reporter.notPerformed("Then I should have $20");
		
		ensureThat(out.toString(), equalTo(
				"Given I have a balance of $50" + NL +
				"When I request $20" + NL +
				"When I ask Liz for a loan of $100" + NL +
				"Then I should have a balance of $30 (PENDING)" + NL +
				"Then I should have $20 (NOT PERFORMED)" + NL));
	}
	
	@Test
	public void shouldOutputThrowablesWhenToldToDoSo() {
		IllegalAccessException exception = new IllegalAccessException("Leave my money alone!");
		ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(stackTrace));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out), true);
		reporter.beforeScenario("A title");
		reporter.successful("Given I have a balance of $50");
		reporter.successful("When I request $20");
		reporter.failed("When I ask Liz for a loan of $100", exception);
		reporter.pending("Then I should have a balance of $30");
		reporter.notPerformed("Then I should have $20");
		reporter.afterScenario();
		
		ensureThat(out.toString(), equalTo(
				"Scenario: A title" + NL + NL +
				"Given I have a balance of $50" + NL +
				"When I request $20" + NL +
				"When I ask Liz for a loan of $100 (FAILED)" + NL +
				"Then I should have a balance of $30 (PENDING)" + NL +
				"Then I should have $20 (NOT PERFORMED)" + NL + NL +
				stackTrace + NL));
		
		out = new ByteArrayOutputStream();
		reporter = new PrintStreamScenarioReporter(new PrintStream(out), false);
		reporter.beforeScenario("A title");
		reporter.successful("Given I have a balance of $50");
		reporter.successful("When I request $20");
		reporter.failed("When I ask Liz for a loan of $100", exception);
		reporter.pending("Then I should have a balance of $30");
		reporter.notPerformed("Then I should have $20");
		reporter.afterScenario();
		
		ensureThat(!out.toString().contains(stackTrace.toString()));
	}


}

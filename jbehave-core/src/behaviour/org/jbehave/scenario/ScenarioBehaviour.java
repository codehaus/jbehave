package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.FileFinder;
import org.jbehave.scenario.parser.StepParser;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;

/**
 * NB: These are integration tests, quite deliberately. Files and string parsing are
 * tricky and fragile, so have been mocked out. If you want to know whether JBehave really works,
 * use the examples.
 */
public class ScenarioBehaviour {

	private static final String NL = System.getProperty("line.separator");

	@Test
	public void shouldPerformStepsInFileAssociatedWithNameUsingGivenStepsClasses() throws Throwable {
		
		FileFinder fileFinder = mock(FileFinder.class);
		StepParser stepParser = mock(StepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScenarioRunner runner = new ScenarioRunner(new PrintStreamScenarioReporter(new PrintStream(output)));
		MySteps steps = new MySteps();
		
 		stub(fileFinder.findFileMatching(MyScenario.class)).toReturn(new ByteArrayInputStream("my_scenario".getBytes()));
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 cows",
				"When I leave them over the winter",
				"Then I have 2 cows"}));

		new MyScenario(fileFinder, stepParser, runner, steps).runUsingSteps();
		
		ensureThat(steps.numberOfCows, equalTo(2));
		ensureThat(output.toString(), equalTo(
				"Given I have 2 cows" + NL + 
				"When I leave them over the winter (PENDING)" + NL +
				"Then I have 2 cows (NOT PERFORMED)" + NL));
	}
	
	@Test
	public void shouldRethrowErrorsInTheEventOfAScenarioFailure() throws Throwable {
		
		FileFinder fileFinder = mock(FileFinder.class);
		StepParser stepParser = mock(StepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScenarioRunner runner = new ScenarioRunner(new PrintStreamScenarioReporter(new PrintStream(output)));
		MySteps steps = new MySteps();
		
		stub(fileFinder.findFileMatching(MyScenario.class)).toReturn(new ByteArrayInputStream("my_scenario".getBytes()));
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 cows",
				"When I put them in a field",
				"Then my cows should not die",
				"Then I have 2 cows"}));
		

		try {
			new MyScenario(fileFinder, stepParser, runner, steps).runUsingSteps();
			fail("Excpected the error to be rethrown");
		} catch (IllegalAccessError e) {
			ensureThat(e, equalTo(steps.error));
		}
		
		ensureThat(output.toString(), equalTo(
				"Given I have 2 cows" + NL + 
				"When I put them in a field" + NL +
				"Then my cows should not die (FAILED)" + NL +
				"Then I have 2 cows (NOT PERFORMED)" + NL));
	}

	
	private static class MyScenario extends Scenario {
		public MyScenario(FileFinder fileFinder, StepParser stepParser, ScenarioRunner scenarioRunner, Steps steps) {
			super(fileFinder, stepParser, scenarioRunner, steps);
		}
	}
	
	public static class MySteps extends Steps {
		
		private int numberOfCows;
		private IllegalAccessError error;

		@Given("I have $n cows")
		public void makeCows(int numberOfCows) {
			this.numberOfCows = numberOfCows;
		}
		
		@When("I put them in a field")
		public void ignoreCows() {}
		
		@Then("I have $n cows")
		public void checkCows(int numberOfCows) {
			ensureThat(this.numberOfCows, equalTo(numberOfCows));
		}
		
		@Then("my cows should not die")
		public void keepCowsAlive() {
			
			error = new IllegalAccessError("Leave my cows alone!");
			throw error;
		}
	}
}

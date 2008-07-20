package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.StepParser;
import org.jbehave.scenario.parser.scenarios.MyPendingScenario;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
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
		
		ScenarioFileLoader fileLoader = mock(ScenarioFileLoader.class);
		StepParser stepParser = mock(PatternStepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScenarioRunner runner = new ScenarioRunner(new PrintStreamScenarioReporter(new PrintStream(output)));
		MySteps steps = new MySteps();
		
 		stub(fileLoader.loadScenarioAsString(MyScenario.class)).toReturn("my_scenario");
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 cows",
				"When I leave them over the winter",
				"Then I have 2 cows"}));

		new MyScenario(fileLoader, stepParser, runner, steps).runUsingSteps();
		
		ensureThat(steps.numberOfCows, equalTo(2));
		ensureThat(output.toString(), equalTo(
				"Given I have 2 cows" + NL + 
				"When I leave them over the winter (PENDING)" + NL +
				"Then I have 2 cows (NOT PERFORMED)" + NL));
	}
	
	@Test
    public void shouldPerformStepsUsingACustomReporter() throws Throwable {
        
        ScenarioFileLoader fileLoader = mock(ScenarioFileLoader.class);
        StepParser stepParser = mock(PatternStepParser.class);
        StringBuffer buffer = new StringBuffer();
        ScenarioRunner runner = new ScenarioRunner(new BufferScenarioReporter(buffer));
        MySteps steps = new MySteps();
        
        stub(fileLoader.loadScenarioAsString(MyScenario.class)).toReturn("my_scenario");
        stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
                "Given I have 2 cows",
                "When I leave them over the winter",
                "Then I have 2 cows"}));

        new MyScenario(fileLoader, stepParser, runner, steps).runUsingSteps();
        
        ensureThat(steps.numberOfCows, equalTo(2));
        ensureThat(buffer.toString(), equalTo(
                "Given I have 2 cows" + NL + 
                "When I leave them over the winter (PENDING)" + NL +
                "Then I have 2 cows (NOT PERFORMED)" + NL));
    }
	
    @Test
    public void shouldPerformStepsUsingScenarioWithDefaults() throws Throwable {        
        new MyPendingScenario().runUsingSteps();
    }
	
	@Test
	public void shouldRethrowErrorsInTheEventOfAScenarioFailure() throws Throwable {
		
		ScenarioFileLoader fileLoader = mock(ScenarioFileLoader.class);
		StepParser stepParser = mock(PatternStepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScenarioRunner runner = new ScenarioRunner(new PrintStreamScenarioReporter(new PrintStream(output)));
		MySteps steps = new MySteps();
		
        stub(fileLoader.loadScenarioAsString(MyScenario.class)).toReturn("my_scenario");
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 cows",
				"When I put them in a field",
				"Then my cows should not die",
				"Then I have 2 cows"}));
		

		try {
			new MyScenario(fileLoader, stepParser, runner, steps).runUsingSteps();
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
		public MyScenario(ScenarioFileLoader fileFinder, StepParser stepParser, ScenarioRunner scenarioRunner, Steps steps) {
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
	
	public static class BufferScenarioReporter implements ScenarioReporter {

        private final StringBuffer buffer;

        public BufferScenarioReporter(StringBuffer buffer) {
            this.buffer = buffer;
        }

        public void failed(String step, Throwable e) {
            buffer.append(step+ " (FAILED)" + NL);
        }

        public void notPerformed(String step) {
            buffer.append(step+ " (NOT PERFORMED)" + NL);
        }

        public void pending(String step) {
            buffer.append(step+ " (PENDING)" + NL);
        }

        public void successful(String step) {
            buffer.append(step+NL);
        }
	    
	}
}

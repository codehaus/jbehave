package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.jbehave.Ensure.ensureThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.jbehave.Configuration;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioDefinition;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.StepParser;
import org.jbehave.scenario.parser.scenarios.MyPendingScenario;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.steps.PendingError;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * NB: These are integration tests, quite deliberately. Files and string parsing are
 * tricky and fragile, so have been mocked out. If you want to know whether JBehave really works,
 * use the examples.
 */
public class ScenarioBehaviour {

	private static final String NL = System.getProperty("line.separator");
	private String originalFailOnPending;
	private String originalPassSilently;

	@Before
	public void captureExistingEnvironmentAndMakeTheseExamplesWork() {
		originalFailOnPending = System.getProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
		originalPassSilently = System.getProperty(PropertyBasedConfiguration.OUTPUT_ALL);
		System.clearProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
		System.clearProperty(PropertyBasedConfiguration.OUTPUT_ALL);
	}
	
	@After
	public void resetEnvironment() {
		if (originalFailOnPending != null) {
			System.setProperty(PropertyBasedConfiguration.FAIL_ON_PENDING, originalFailOnPending);
		} else {
			System.clearProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
		}
		if (originalPassSilently != null) {
			System.setProperty(PropertyBasedConfiguration.OUTPUT_ALL, originalPassSilently);
		} else {
			System.clearProperty(PropertyBasedConfiguration.OUTPUT_ALL);
		}
	}
	
	@Test
	public void shouldPerformStepsInFileAssociatedWithNameUsingGivenStepsClasses() throws Throwable {
		
		ScenarioFileLoader fileLoader = mock(ScenarioFileLoader.class);
		StepParser stepParser = mock(PatternStepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStreamScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(output));
		MySteps steps = new MySteps();
		
 		stub(fileLoader.loadStepsFor(MyScenario.class)).toReturn(Arrays.asList(
 				new ScenarioDefinition(stepParser, "my_scenario")));
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 scenarios",
				"When I do something unexpected",
				"Then I should have 2 scenarios"}));

		new MyScenario(fileLoader, stepParser, reporter, steps).runUsingSteps();
		
		ensureThat(steps.numberOfScenarios, equalTo(2));
		ensureThat(output.toString(), equalTo(
				"Given I have 2 scenarios" + NL + 
				"When I do something unexpected (PENDING)" + NL +
				"Then I should have 2 scenarios (NOT PERFORMED)" + NL));
	}
	
	@Test
    public void shouldPerformStepsUsingACustomReporter() throws Throwable {
        
        ScenarioDefiner fileLoader = mock(ScenarioDefiner.class);
        StepParser stepParser = mock(PatternStepParser.class);
        StringBuffer buffer = new StringBuffer();
        ScenarioReporter reporter = new BufferScenarioReporter(buffer);
        MySteps steps = new MySteps();
        
        stub(fileLoader.loadStepsFor(MyScenario.class)).toReturn(Arrays.asList(
        		new ScenarioDefinition(stepParser, "my_scenario")));
        stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
                "Given I have 2 scenarios",
                "When I do something unexpected",
                "Then I should have 2 scenarios"}));

        new MyScenario(fileLoader, stepParser, reporter, steps).runUsingSteps();
        
        ensureThat(steps.numberOfScenarios, equalTo(2));
        ensureThat(buffer.toString(), equalTo(
                "Given I have 2 scenarios" + NL + 
                "When I do something unexpected (PENDING)" + NL +
                "Then I should have 2 scenarios (NOT PERFORMED)" + NL));
    }
	
    @Test
    public void shouldPerformStepsUsingScenarioWithDefaults() throws Throwable {        
        new MyPendingScenario().runUsingSteps();
    }
    
    @Test
    public void shouldAllowPendingStepsToFailTheBuild() throws Throwable {
    	System.setProperty(PropertyBasedConfiguration.FAIL_ON_PENDING, "true");
		try {
			new MyPendingScenario().runUsingSteps();
			fail("Should not have run successfully");
		} catch (AssertionError e) {
			ensureThat(e, is(PendingError.class));
		}
		
		try {
			new MyPendingScenario().runUsingSteps();
			fail("Should not have run successfully");
		} catch (AssertionError e) {
			ensureThat(e, is(PendingError.class));
		}
    }
    
    @Test
    public void shouldAllowPassingScenariosToBeSilent() throws Throwable {
    	System.setProperty(PropertyBasedConfiguration.OUTPUT_ALL, "true");
        
    	// The only way to test this would be to replace the reporter in the configuration.
    	// As a JBehave user, I want to change reporters so that the reporter I specify 
    	// is the one that's used. Doing anything which would allow this to be testable
    	// would conflict with that intuitive use! So, 
    }
    
    @Test
    public void shouldAllowPartlyDefinedStepsToExplicitlyThrowPendingErrors() throws Throwable {
        ScenarioDefiner fileLoader = mock(ScenarioDefiner.class);
        StepParser stepParser = mock(PatternStepParser.class);
        StringBuffer buffer = new StringBuffer();
        ScenarioReporter reporter = new BufferScenarioReporter(buffer);
        MySteps steps = new MySteps();
        
        stub(fileLoader.loadStepsFor(MyScenario.class)).toReturn(Arrays.asList(
        		new ScenarioDefinition(stepParser, "my_scenario")));
        stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
                "Given I have 2 scenarios",
                "When I read my scenarios",
                "Then my scenario should be pending"}));

        new MyScenario(fileLoader, stepParser, reporter, steps).runUsingSteps();
        
        ensureThat(steps.numberOfScenarios, equalTo(2));
        ensureThat(buffer.toString(), equalTo(
                "Given I have 2 scenarios" + NL + 
                "When I read my scenarios" + NL +
                "Then my scenario should be pending (PENDING)" + NL));
    }
	
	@Test
	public void shouldRethrowErrorsInTheEventOfAScenarioFailure() throws Throwable {
		
		ScenarioDefiner scenarioDefiner = mock(ScenarioDefiner.class);
		StepParser stepParser = mock(PatternStepParser.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(output));
		MySteps steps = new MySteps();
		
        stub(scenarioDefiner.loadStepsFor(MyScenario.class)).toReturn(Arrays.asList(
        		new ScenarioDefinition(stepParser, "my_scenario")));
		stub(stepParser.findSteps("my_scenario")).toReturn(Arrays.asList(new String[] {
				"Given I have 2 scenarios",
				"When I read my scenarios",
				"Then my scenario should fail",
				"Then I should have 2 scenarios"}));
		
		try {
			new MyScenario(scenarioDefiner, stepParser, reporter, steps).runUsingSteps();
			fail("Expected the error to be rethrown");
		} catch (IllegalAccessError e) {
			ensureThat(e, equalTo(steps.error));
		}
		
		ensureThat(output.toString(), equalTo(
				"Given I have 2 scenarios" + NL + 
				"When I read my scenarios" + NL +
				"Then my scenario should fail (FAILED)" + NL +
				"Then I should have 2 scenarios (NOT PERFORMED)" + NL));
	}

	
	private static class MyScenario extends Scenario {
		public MyScenario(final ScenarioDefiner scenarioDefiner, final StepParser stepParser, final ScenarioReporter scenarioReporter, Steps steps) {
			super(new Configuration() {

				public ScenarioDefiner forDefiningScenarios() { return scenarioDefiner; }

				public ScenarioReporter forReportingScenarios() { return scenarioReporter; }

				public PendingStepStrategy forPendingSteps() { return PendingStepStrategy.PASSING; }
				
			}, steps);
		}
	}
	
	public static class MySteps extends Steps {
		
		private int numberOfScenarios;
		private IllegalAccessError error;

		@Given("I have $n scenarios")
		public void makeSomeScenarios(int numberOfScenarios) {
			this.numberOfScenarios = numberOfScenarios;
		}
		
		@When("I read my scenarios")
		public void readScenarios() {}
		
		@Then("I should have $n scenarios")
		public void checkNumberOfScenarios(int numberOfCows) {
			ensureThat(this.numberOfScenarios, equalTo(numberOfCows));
		}
		
		@Then("my scenario should fail")
		public void makeTheScenarioFail() {
			error = new IllegalAccessError("Die, Scenario, Die!");
			throw error;
		}
		
		@Then("my scenario should be pending")
		public void pending() {
			throw new PendingError("Cows are waiting");
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

		public void afterScenario() {
			// TODO Auto-generated method stub
			
		}

		public void beforeScenario(String blurb) {
			// TODO Auto-generated method stub
			
		}
	    
	}
}

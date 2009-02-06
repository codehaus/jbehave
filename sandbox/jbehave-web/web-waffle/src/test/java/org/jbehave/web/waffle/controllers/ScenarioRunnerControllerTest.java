package org.jbehave.web.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;


public class ScenarioRunnerControllerTest {

	private static final Menu MENU = new Menu();
	private static final Configuration CONFIGURATION = new MostUsefulConfiguration();
	private static final ScenarioParser SCENARIO_PARSER = new PatternScenarioParser();
	private static final ScenarioRunner SCENARIO_RUNNER = new ScenarioRunner();
	private static final String NL = "\n";

	@Test
	public void canRunSuccessfulScenario(){
		ScenarioRunnerController controller = new ScenarioRunnerController(MENU, CONFIGURATION, SCENARIO_PARSER, SCENARIO_RUNNER, new MySteps());
		String scenarioInput = "Scenario: A simple test" + NL 
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper"; 
		String scenarioOutput = scenarioInput; // if successfully, input=output
		controller.getScenarioContext().setInput(scenarioInput);
		controller.run();
		assertEquals(scenarioOutput, controller.getScenarioContext().getOutput().trim());
	}

	@Test
	public void canRunFailingScenario(){
		ScenarioRunnerController controller = new ScenarioRunnerController(MENU, CONFIGURATION, SCENARIO_PARSER, SCENARIO_RUNNER, new MySteps());
		String scenarioInput = "Scenario: A simple test" + NL 
						+ NL
						+ "Given a test" + NL
						+ "When a test fails" + NL
						+ "Then a tester is a happy hopper"; 
		String scenarioOutput = "Scenario: A simple test" + NL 
						+ NL
						+ "Given a test" + NL
						+ "When a test fails (FAILED)" + NL
						+ "Then a tester is a happy hopper (NOT PERFORMED)"; 
		controller.getScenarioContext().setInput(scenarioInput);
		controller.run();
		assertEquals(scenarioOutput, controller.getScenarioContext().getOutput().trim());
		assertEquals(asList("Test failed"), controller.getScenarioContext().getMessages());
	}
	
	public static class MySteps extends Steps {
		@Given("a test")
		public void aTest(){
		}
		@When("a test is executed")
		public void aTestIsExecuted(){
		}
		@When("a test fails")
		public void aTestFails(){
			throw new RuntimeException("Test failed");
		}
		@Then("a tester is a happy hopper")
		public void aTesterIsHappy(){
		}
	};

}

package org.jbehave.web.waffle.controllers;

import static org.junit.Assert.assertEquals;

import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.web.waffle.controllers.ScenarioRunnerController;
import org.junit.Test;


public class ScenarioRunnerControllerTest {

	private static final Configuration CONFIGURATION = new MostUsefulConfiguration();
	private static final ScenarioParser SCENARIO_PARSER = new PatternScenarioParser();
	private static final ScenarioRunner SCENARIO_RUNNER = new ScenarioRunner();
	private static final String NL = "\n";

	@Test
	public void canRunScenario(){
		ScenarioRunnerController controller = new ScenarioRunnerController(CONFIGURATION, SCENARIO_PARSER, SCENARIO_RUNNER, new MySteps());
		String scenarioInput = "Scenario: A simple test" + NL 
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper"; 
		controller.getScenarioData().setInput(scenarioInput);
		controller.run();
		assertEquals(scenarioInput, controller.getScenarioData().getOutput().trim());
	}
	
	public static class MySteps extends Steps {
		@Given("a test")
		public void aTest(){
		}
		@When("a test is executed")
		public void aTestIsExecuted(){
		}
		@Then("a tester is a happy hopper")
		public void aTesterIsHappy(){
		}
	};

}

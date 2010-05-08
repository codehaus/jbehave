package org.jbehave.examples.trader.scenarios;

import junit.framework.Assert;

import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.jbehave.scenario.steps.StepsFactory;

public class FailureFollowedByGivenScenarios extends JUnitScenario {

	public FailureFollowedByGivenScenarios() {
		useConfiguration(new MostUsefulConfiguration() {
			@Override
			public ScenarioDefiner forDefiningScenarios() {
				return new ClasspathScenarioDefiner(
						new UnderscoredCamelCaseResolver(".scenario"),
						new PatternScenarioParser(keywords()));
			}

			@Override
			public ScenarioReporter forReportingScenarios() {
				return new PrintStreamScenarioReporter();
			}

		});

		StepsConfiguration configuration = new StepsConfiguration();
		addSteps(new StepsFactory(configuration)
				.createCandidateSteps(new SandpitSteps()));
	}

	public static class SandpitSteps extends Steps {

		@Given("I do nothing")
		public void doNothing() {
		}

		@Then("I fail")
		public void doFail() {
			Assert.fail("I failed!");
		}

		@Then("I pass")
		public void doPass() {
		}
	}

}

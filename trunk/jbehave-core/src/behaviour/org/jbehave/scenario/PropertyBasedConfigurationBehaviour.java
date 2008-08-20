package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.is;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.scenario.reporters.PassSilentlyDecorator;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PropertyBasedConfigurationBehaviour {

	private String originalFailOnPending;

	@Before
	public void captureExistingEnvironment() {
		originalFailOnPending = System.getProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
	}
	
	@After
	public void resetEnvironment() {
		if (originalFailOnPending != null) {
			System.setProperty(PropertyBasedConfiguration.FAIL_ON_PENDING, originalFailOnPending);
		} else {
			System.clearProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
		}
	}
	
	@Test
	public void shouldUsePassingPendingStepStrategyByDefault() {
		System.clearProperty(PropertyBasedConfiguration.FAIL_ON_PENDING);
		ensureThat(new PropertyBasedConfiguration().forPendingSteps(), is(PendingStepStrategy.PASSING));
	}
	
	@Test
	public void shouldUseFailingPendingStepStrategyWhenConfiguredToDoSo() {
		System.setProperty(PropertyBasedConfiguration.FAIL_ON_PENDING, "true");
		ensureThat(new PropertyBasedConfiguration().forPendingSteps(), is(PendingStepStrategy.FAILING));
	}
	
	@Test
	public void shouldSwallowOutputFromPassingScenariosByDefault() {
		System.clearProperty(PropertyBasedConfiguration.OUTPUT_ALL);
		ensureThat(new PropertyBasedConfiguration().forReportingScenarios(), is(PassSilentlyDecorator.class));
	}
	
	@Test
	public void shouldOutputAllWhenConfiguredToDoSo() {
		System.setProperty(PropertyBasedConfiguration.OUTPUT_ALL, "true");
		ensureThat(new PropertyBasedConfiguration().forReportingScenarios(), is(PrintStreamScenarioReporter.class));
	}
}

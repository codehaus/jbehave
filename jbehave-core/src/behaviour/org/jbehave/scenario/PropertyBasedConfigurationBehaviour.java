package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.is;
import static org.jbehave.Ensure.ensureThat;

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
}

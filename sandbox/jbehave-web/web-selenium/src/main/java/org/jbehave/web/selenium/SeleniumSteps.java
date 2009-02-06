package org.jbehave.web.selenium;

import java.util.concurrent.TimeUnit;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.steps.Steps;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.Condition;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;

public class SeleniumSteps extends Steps {

	private final Selenium selenium;
	private final ConditionRunner runner;

	public SeleniumSteps() {
		this.selenium = createSelenium();
		this.runner = createConditionRunner(selenium);
	}

	@BeforeScenario
	public void setUp() throws Exception {
		selenium.start();
	}

	@AfterScenario
	public void tearDown() throws Exception {
		selenium.close();
		selenium.stop();
	}

	protected Selenium createSelenium() {
		return new DefaultSelenium("localhost", 4444, "*firefox",
				"http://localhost:8080");
	}

	protected JUnitConditionRunner createConditionRunner(Selenium selenium) {
		return new JUnitConditionRunner(selenium, 10, 100, 1000);
	}

	protected void waitFor(Condition condition) {
		runner.waitFor(condition);
		waitFor(2);
	}

	protected void waitFor(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// continue
		}
	}

}

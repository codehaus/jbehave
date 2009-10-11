package org.jbehave.web.selenium;

import org.jbehave.scenario.steps.SilentStepMonitor;
import org.jbehave.scenario.steps.StepMonitor;
import org.jbehave.scenario.steps.StepsConfiguration;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStepsConfiguration extends StepsConfiguration {

	private final Selenium selenium;

	public SeleniumStepsConfiguration(Selenium selenium, SeleniumContext seleniumContext) {
		this(selenium, seleniumContext, new SilentStepMonitor());
	}

	public SeleniumStepsConfiguration(Selenium selenium, SeleniumContext seleniumContext, StepMonitor stepMonitor) {
		this.selenium = selenium;
		useMonitor(new SeleniumStepsMonitor(selenium, seleniumContext, stepMonitor));
	}

	public Selenium getSelenium() {
		return selenium;
	}

}

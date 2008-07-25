package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

public interface ScenarioDefiner {

	String loadStepsFor(Class<? extends Scenario> clazz);

}
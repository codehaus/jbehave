package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

public interface ScenarioDefiner {

	ScenarioDefinition loadStepsFor(Class<? extends Scenario> clazz);

}
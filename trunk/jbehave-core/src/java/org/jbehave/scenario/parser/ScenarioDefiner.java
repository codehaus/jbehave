package org.jbehave.scenario.parser;

import java.util.List;

import org.jbehave.scenario.Scenario;

public interface ScenarioDefiner {

	List<ScenarioDefinition> loadStepsFor(Class<? extends Scenario> clazz);

}
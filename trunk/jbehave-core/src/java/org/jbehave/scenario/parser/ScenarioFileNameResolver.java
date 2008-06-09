package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

public interface ScenarioFileNameResolver {

	String resolve(Class<? extends Scenario> scenarioClass);

}

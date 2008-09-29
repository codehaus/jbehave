package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

/**
 * <p>
 * Resolves scenario names converting the Java scenario class to a resource
 * path.
 * </p>
 */
public interface ScenarioNameResolver {

    String resolve(Class<? extends Scenario> scenarioClass);

}

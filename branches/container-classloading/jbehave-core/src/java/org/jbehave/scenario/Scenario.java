package org.jbehave.scenario;

/**
 * <p>
 * Scenario represents the main interface to run a scenario.
 * </p>
 * <p>
 * Typically users will need to extend an abstract implementation, such as
 * {@link AbstractScenario} or a decorator scenarios, such as
 * {@link JUnitScenario}, which also provide support for test frameworks.
 * </p>
 * 
 * @see AbstractScenario
 * @see JUnitScenario
 */
public interface Scenario {

    void runScenario() throws Throwable;

}

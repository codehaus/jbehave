package org.jbehave.scenario;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.parser.ScenarioNameResolver;

/**
 * <p>
 * Scenario represents the main interface to run a scenario.
 * </p>
 * <p>
 * Extend the implementation that uses the test framework of your choice, eg
 * JUnitScenario, and call the class after your scenario, eg: "ICanLogin.java".
 * </p>
 * <p>
 * Your Scenario class should be in a matching text file in the same place, eg
 * "i_can_login". The scenario name used can be configured via the
 * {@link ScenarioNameResolver}.
 * </p>
 * <p>
 * Write some steps in your text scenario, starting each new step with Given,
 * When, Then or And. The keywords can be configured via the {@link KeyWords}
 * class, eg they can be translated/localized to other languages.
 * </p>
 * <p>
 * Then move on to extending the Steps class.
 * </p>
 */
public interface Scenario {

    void runScenario() throws Throwable;

}

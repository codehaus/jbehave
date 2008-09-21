package org.jbehave.scenario;

import junit.framework.TestCase;

import org.jbehave.scenario.steps.Steps;
import org.junit.Test;

/**
 * <p>
 * Scenario decorator that add supports for running scenarios as <a
 * href="http://junit.org">JUnit</a> tests. Both JUnit 4.x (via @Test
 * annotation) and JUnit 3.8.x (via TestCase inheritance) are supported.
 * </p>
 * <p>
 * Users requiring JUnit support will extends this class instead of
 * {@link AbstractScenario}, while providing the same dependencies and following
 * the same Scenario specification logic as described in
 * {@link AbstractScenario}. The only difference in the dependencies provided is
 * that the scenario class is automatically set to the one being implemented by
 * the user, ie the concrete decorator class.
 * </p>
 * 
 * @see AbstractScenario
 */
public abstract class JUnitScenario extends TestCase implements Scenario {

    private final Class<? extends JUnitScenario> decoratorClass = this.getClass();
    private final Scenario delegate;

    public JUnitScenario(Steps... candidateSteps) {
        this.delegate = new JUnitScenarioDelegate(decoratorClass, candidateSteps);
    }

    public JUnitScenario(Configuration configuration, Steps... candidateSteps) {
        this.delegate = new JUnitScenarioDelegate(decoratorClass, configuration, candidateSteps);
    }

    public JUnitScenario(ScenarioRunner scenarioRunner, Configuration configuration, Steps... candidateSteps) {
        this.delegate = new JUnitScenarioDelegate(decoratorClass, scenarioRunner, configuration, candidateSteps);
    }

    public JUnitScenario(Scenario delegate) {
        this.delegate = delegate;

    }

    @Test
    public void runScenario() throws Throwable {
        this.delegate.runScenario();
    }

    /**
     * A JUnit 3-compatibile runnable method which simply delegates
     * {@link Scenario#runScenario()}
     * 
     * @throws Throwable
     */
    public void testScenario() throws Throwable {
        runScenario();
    }

    public static class JUnitScenarioDelegate extends AbstractScenario {

        public JUnitScenarioDelegate(Class<? extends Scenario> decoratorClass, Steps... candidateSteps) {
            super(decoratorClass, candidateSteps);
        }

        public JUnitScenarioDelegate(Class<? extends Scenario> decoratorClass, Configuration configuration,
                Steps... candidateSteps) {
            super(decoratorClass, configuration, candidateSteps);
        }

        public JUnitScenarioDelegate(Class<? extends Scenario> decoratorClass, ScenarioRunner scenarioRunner,
                Configuration configuration, Steps... candidateSteps) {
            super(decoratorClass, scenarioRunner, configuration, candidateSteps);
        }

    }

}

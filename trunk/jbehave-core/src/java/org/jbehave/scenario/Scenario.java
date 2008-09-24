package org.jbehave.scenario;

import org.jbehave.scenario.steps.Steps;

/**
 * <p>
 * Scenario is deprecated to allow for support of multiple test frameworks.
 * Users should extend a scenario class that supports the desired test framework.
 * </p>
 * 
 * @deprecated Since 2.1 use JUnitScenario
 */
public abstract class Scenario extends JUnitScenario {

    public Scenario(Steps... candidateSteps) {
        super(candidateSteps);
    }
    
    public Scenario(Configuration configuration, Steps... candidateSteps) {
        super(configuration, candidateSteps);
    }

    public Scenario(ScenarioRunner scenarioRunner, Configuration configuration, Steps... candidateSteps) {
        super(scenarioRunner, configuration, candidateSteps);
    }

    public Scenario(RunnableScenario delegate) {
        super(delegate);
    }

}

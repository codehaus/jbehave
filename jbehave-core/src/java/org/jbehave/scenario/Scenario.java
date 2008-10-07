package org.jbehave.scenario;

import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;

/**
 * <p>
 * Extend this class to run your scenario. Call the class after your scenario,
 * eg: "ICanLogin.java".
 * </p>
 * <p>
 * The Scenario should be in a matching text file in the same place, eg:
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
public abstract class Scenario {

    private final Configuration configuration;
    private final ScenarioRunner scenarioRunner;
    private final Steps[] candidateSteps;

    public Scenario(Steps... candidateSteps) {
        this(new PropertyBasedConfiguration(), candidateSteps);
    }

    public Scenario(Configuration configuration, Steps... candidateSteps) {
        this(new ScenarioRunner(), configuration, candidateSteps);
    }

    public Scenario(ScenarioRunner scenarioRunner, Configuration configuration, Steps... candidateSteps) {
        this.configuration = configuration;
        this.scenarioRunner = scenarioRunner;
        this.candidateSteps = candidateSteps;
    }

    @Test
    public void run() throws Throwable {
        StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(this.getClass());
        scenarioRunner.run(story, configuration, candidateSteps);
    }
}

package org.jbehave.scenario;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.parser.ScenarioNameResolver;
import org.jbehave.scenario.steps.CandidateSteps;

/**
 * <p>
 * Abstract implementation of Scenario which is primarily intended as a base
 * class for delegate implementations of Scenarios. As such, it has no explicit
 * supports for any test framework, ie it requires the {@link runScenario}
 * method to be invoked directly, and the class of the scenario being run needs
 * to be provided explicitly.
 * </p>
 * <p>
 * Typically, users will find it easier to extend decorator scenarios, such as
 * {@link JUnitScenario} which also provide support for test frameworks and also
 * provide the scenario class as the one being implemented by the user.
 * </p>
 * <p>
 * Whichever Scenario class one chooses to extends, the steps for running a
 * scenario are the same:
 * <ol>
 * <li>Extend the chosen scenario class and name it after your scenario, eg
 * "ICanLogin.java" (note that there is no obligation to have the name of the
 * class end in "Scenario" although you may choose to).</li>
 * <li>The scenario class should be in a matching text file in the same place,
 * eg "i_can_login" (this uses the default name resolution, although the it can
 * be configured via the {@link ScenarioNameResolver}).</li>
 * <li>Write some steps in your text scenario, starting each new step with
 * Given, When, Then or And. The keywords can be configured via the
 * {@link KeyWords} class, eg they can be translated/localized to other
 * languages.</li>
 * <li>Then move on to extending the Steps class and providing matching methods
 * for the steps defined in the text scenario.</li>
 * <ol>
 */
public abstract class AbstractScenario implements RunnableScenario {

    private final Configuration configuration;
    private final ScenarioRunner scenarioRunner;
    private final CandidateSteps[] candidateSteps;
    private final Class<? extends RunnableScenario> scenarioClass;

    public AbstractScenario(Class<? extends RunnableScenario> scenarioClass, CandidateSteps... candidateSteps) {
        this(scenarioClass, new PropertyBasedConfiguration(), candidateSteps);
    }

    public AbstractScenario(Class<? extends RunnableScenario> scenarioClass, Configuration configuration,
            CandidateSteps... candidateSteps) {
        this(scenarioClass, new ScenarioRunner(), configuration, candidateSteps);
    }

    public AbstractScenario(Class<? extends RunnableScenario> scenarioClass, ScenarioRunner scenarioRunner,
            Configuration configuration, CandidateSteps... candidateSteps) {
        this.scenarioClass = scenarioClass;
        this.configuration = configuration;
        this.scenarioRunner = scenarioRunner;
        this.candidateSteps = candidateSteps;
    }

    public void runScenario() throws Throwable {
        StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(scenarioClass);
        scenarioRunner.run(story, configuration, candidateSteps);
    }

}

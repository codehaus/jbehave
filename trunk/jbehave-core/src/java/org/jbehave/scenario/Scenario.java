package org.jbehave.scenario;

import org.jbehave.Configuration;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;

/**
 * <p>
 * A scenario is a collection of candidate steps that need to be run using a scenario runner.
 * </p>
 * <p>Users extend Scenario by providing candidate steps appropriate for the behaviour</p>
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 */
public abstract class Scenario {

    private final Steps[] candidateSteps;
    private final ScenarioRunner scenarioRunner;
	private final Configuration configuration;

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
    public void runUsingSteps() throws Throwable {
        StoryDefinition story = configuration.forDefiningScenarios().loadScenarioDefinitionsFor(this.getClass());
        scenarioRunner.run(story, configuration, candidateSteps);
    }
}

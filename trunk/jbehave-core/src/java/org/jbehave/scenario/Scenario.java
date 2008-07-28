package org.jbehave.scenario;

import java.util.List;

import org.jbehave.OurTechnique;
import org.jbehave.Technique;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioDefinition;
import org.jbehave.scenario.steps.CandidateStep;
import org.jbehave.scenario.steps.PendingStep;
import org.jbehave.scenario.steps.Step;
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
    private final ScenarioDefiner scenarioDefiner;
    private final ScenarioRunner scenarioRunner;

    public Scenario(Steps... candidateSteps) {
        this(new OurTechnique(), candidateSteps);
    }

    public Scenario(Technique technique, Steps... candidateSteps) {
        this.candidateSteps = candidateSteps;
		this.scenarioDefiner = technique.forDefiningScenarios();
        this.scenarioRunner = new ScenarioRunner(technique.forReportingScenarios());
    }

    @Test
    public void runUsingSteps() throws Throwable {
        List<ScenarioDefinition> definitions = scenarioDefiner.loadStepsFor(this.getClass());
        for (ScenarioDefinition definition : definitions) {
            Step[] steps = createRealStepsFromCandidates(definition.getSteps());
            scenarioRunner.run(steps);
		}
    }

	private Step[] createRealStepsFromCandidates(List<String> stringSteps) {
		Step[] steps = new Step[stringSteps.size()];
        for (int i = 0; i < steps.length; i++) {
            String stringStep = stringSteps.get(i);
            for (Steps candidates : candidateSteps) {
                for (CandidateStep candidate : candidates.getSteps()) {
                    if (candidate.matches(stringStep)) {
                        steps[i] = candidate.createFrom(stringStep);
                    }
                }
            }
            if (steps[i] == null) {
                steps[i] = new PendingStep(stringStep);
            }
        }
		return steps;
	}
}

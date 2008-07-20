package org.jbehave.scenario;

import java.util.List;

import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.StepParser;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
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
    private final ScenarioFileLoader fileLoader;
    private final StepParser stepParser;
    private final ScenarioRunner scenarioRunner;

    public Scenario(Steps... candidateSteps) {
        this(new ScenarioFileLoader(), candidateSteps);
    }

    public Scenario(ScenarioFileLoader fileLoader, Steps... candidateSteps) {
        this(fileLoader, new PrintStreamScenarioReporter(), candidateSteps);
    }

    public Scenario(ScenarioFileLoader fileLoader, ScenarioReporter reporter, Steps... candidateSteps) {
        this(fileLoader, new PatternStepParser(), new ScenarioRunner(reporter), candidateSteps);
    }

    public Scenario(ScenarioFileLoader fileFinder, StepParser stepParser, ScenarioRunner scenarioRunner,
            Steps... candidateSteps) {
        this.fileLoader = fileFinder;
        this.stepParser = stepParser;
        this.scenarioRunner = scenarioRunner;
        this.candidateSteps = candidateSteps;
    }

    @Test
    public void runUsingSteps() throws Throwable {
        List<String> stringSteps = stepParser.findSteps(fileLoader.loadScenarioAsString(this.getClass()));
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
        scenarioRunner.run(steps);
    }
}

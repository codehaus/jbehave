package org.jbehave.scenario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.StepParser;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.steps.PendingStep;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;

public abstract class Scenario {

    private final Steps[] candidateSteps;
    private final ScenarioFileLoader fileLoader;
    private final StepParser stepParser;
    private final ScenarioRunner scenarioRunner;

    public Scenario(Steps... candidateSteps) {
        this(new ScenarioFileLoader(), new StepParser(),
                new ScenarioRunner(new PrintStreamScenarioReporter()), candidateSteps);
    }

    public Scenario(ScenarioFileLoader fileLoader, Steps... candidateSteps) {
        this(fileLoader, new StepParser(), new ScenarioRunner(new PrintStreamScenarioReporter()),
                candidateSteps);
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
        List<String> stringSteps = stepParser.findSteps(convertToString());
        Step[] steps = new Step[stringSteps.size()];
        for (int i = 0; i < steps.length; i++) {
            String stringStep = stringSteps.get(i);
            for (Steps candidates : candidateSteps) {
                for (CandidateStep candidate : candidates.getSteps()) {
                    if (candidate.matches(stringStep)) {
                        steps[i] = candidate.createFrom(stringStep);
                    }
                }
                if (steps[i] == null) {
                    steps[i] = new PendingStep(stringStep);
                }
            }
        }
        scenarioRunner.run(steps);
    }

    private String convertToString() {
        try {
            InputStream stream = fileLoader.loadScenarioFor(this.getClass());
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(bytes);
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

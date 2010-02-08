package org.jbehave.scenario.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;

/**
 * StepCreator that marks unmatched steps as {@link StepResult.Pending}
 */
public class UnmatchedToPendingStepCreator implements StepCreator {

    public Step[] createStepsFrom(StoryDefinition storyDefinition, Stage stage, CandidateSteps... candidateSteps) {
        List<Step> steps = new ArrayList<Step>();
        for (CandidateSteps candidates : candidateSteps) {
            switch (stage) {
                case BEFORE:
                    steps.addAll(candidates.runBeforeStory());
                    break;
                case AFTER:
                    steps.addAll(candidates.runAfterStory());
                    break;
                default:
                    break;
            }
        }
        return steps.toArray(new Step[steps.size()]);
    }

    public Step[] createStepsFrom(ScenarioDefinition scenario, Map<String, String> tableRow,
            CandidateSteps... candidateSteps) {
        List<Step> steps = new ArrayList<Step>();

        addMatchedScenarioSteps(scenario, steps, tableRow, candidateSteps);
        addBeforeAndAfterScenarioSteps(steps, candidateSteps);

        return steps.toArray(new Step[steps.size()]);
    }

    private void addBeforeAndAfterScenarioSteps(List<Step> steps, CandidateSteps[] candidateSteps) {
        for (CandidateSteps candidates : candidateSteps) {
            steps.addAll(0, candidates.runBeforeScenario());
        }

        for (CandidateSteps candidates : candidateSteps) {
            steps.addAll(candidates.runAfterScenario());
        }
    }

    private void addMatchedScenarioSteps(ScenarioDefinition scenarioDefinition, List<Step> steps,
            Map<String, String> tableRow, CandidateSteps... candidateSteps) {
        for (String stringStep : scenarioDefinition.getSteps()) {
            Step step = new PendingStep(stringStep);
            for (CandidateSteps candidates : candidateSteps) {
                for (CandidateStep candidate : candidates.getSteps()) {
                    if (candidate.ignore(stringStep)) { // ignorable steps are added so they can be reported
                        step = new IgnorableStep(stringStep);
                        break;
                    }                        
                    if (candidate.matches(stringStep)) {
                        step = candidate.createFrom(tableRow, stringStep);
                        break;
                    }
                }
            }
            steps.add(step);
        }
    }

}

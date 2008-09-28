package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import org.jbehave.scenario.definition.ScenarioDefinition;
import org.junit.Test;

public class UnmatchedToPendingStepCreatorBehaviour {

    @Test
    public void shouldMatchUpStepsAndScenarioDefinitionToCreateExecutableSteps() {
        // Given
        UnmatchedToPendingStepCreator creator = new UnmatchedToPendingStepCreator();
        
        CandidateStep candidate = mock(CandidateStep.class);
        CandidateSteps steps = mock(Steps.class);
        Step executableStep = mock(Step.class);
        
        stub(candidate.matches("my step")).toReturn(true);
        stub(candidate.createFrom("my step")).toReturn(executableStep);
        stub(steps.getSteps()).toReturn(new CandidateStep[] {candidate});
        
        // When
        Step[] executableSteps = creator.createStepsFrom(new ScenarioDefinition("", "my step"), steps);
        
        // Then
        ensureThat(executableSteps.length, equalTo(1));
        ensureThat(executableSteps[0], equalTo(executableStep));
    }

    @Test
    public void shouldProvidePendingStepsForAnyStepsWhichAreNotAvailable() {
        // Given
        UnmatchedToPendingStepCreator creator = new UnmatchedToPendingStepCreator();
        
        CandidateStep candidate = mock(CandidateStep.class);
        CandidateSteps steps = mock(Steps.class);
        Step executableStep = mock(Step.class);
        
        stub(candidate.matches("my step")).toReturn(false);
        stub(candidate.createFrom("my step")).toReturn(executableStep);
        stub(steps.getSteps()).toReturn(new CandidateStep[] {candidate});
        
        // When
        Step[] executableSteps = creator.createStepsFrom(new ScenarioDefinition("", "my step"), steps);
        
        // Then
        ensureThat(executableSteps.length, equalTo(1));
        StepResult result = executableSteps[0].perform();
        ensureThat(result.getThrowable().getMessage(), equalTo("Pending: my step"));
    }
}

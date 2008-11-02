package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsArray.array;
import static org.jbehave.Ensure.ensureThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;

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
        
        stub(candidate.matches("my step")).toReturn(false);
        stub(steps.getSteps()).toReturn(new CandidateStep[] {candidate});
        
        // When
        Step[] executableSteps = creator.createStepsFrom(new ScenarioDefinition("", "my step"), steps);
        
        // Then
        ensureThat(executableSteps.length, equalTo(1));
        StepResult result = executableSteps[0].perform();
        ensureThat(result.getThrowable().getMessage(), equalTo("Pending: my step"));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void shouldPrependBeforeScenarioAndAppendAfterScenarioAnnotatedSteps() {
    	//Given some steps classes which run different steps before and after scenarios
    	Steps steps1 = mock(Steps.class);
    	Steps steps2 = mock(Steps.class);
    	Step stepBefore1 = mock(Step.class);
    	Step stepBefore2 = mock(Step.class);
    	Step stepAfter1 = mock(Step.class);
    	Step stepAfter2 = mock(Step.class);

    	stub(steps1.runBeforeScenario()).toReturn(Arrays.asList(stepBefore1));
    	stub(steps2.runBeforeScenario()).toReturn(Arrays.asList(stepBefore2));
    	stub(steps1.runAfterScenario()).toReturn(Arrays.asList(stepAfter1));
    	stub(steps2.runAfterScenario()).toReturn(Arrays.asList(stepAfter2));

    	// And which have a 'normal' step that matches our scenario
        CandidateStep candidate = mock(CandidateStep.class);
        Step normalStep = mock(Step.class);
        
        stub(candidate.matches("my step")).toReturn(true);
        stub(candidate.createFrom("my step")).toReturn(normalStep);
        stub(steps1.getSteps()).toReturn(new CandidateStep[] {candidate});
        stub(steps2.getSteps()).toReturn(new CandidateStep[] {});
    	
        // When we create the series of steps for the scenario
    	UnmatchedToPendingStepCreator creator = new UnmatchedToPendingStepCreator();
    	Step[] executableSteps = creator.createStepsFrom(new ScenarioDefinition("", "my step"), steps1, steps2);
    	
    	// Then all before and after steps should be added
    	ensureThat(executableSteps, array(equalTo(stepBefore2), equalTo(stepBefore1), equalTo(normalStep), equalTo(stepAfter1), equalTo(stepAfter2)));
    }

}

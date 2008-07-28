package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;
import org.junit.Test;

public class ScenarioRunnerBehaviour {

	@Test
	public void shouldPerformStepsThenDescribeThemToReporter() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step step = mock(Step.class);
		StepResult result = mock(StepResult.class);
		ScenarioRunner runner = new ScenarioRunner(reporter);
		
		stub(step.perform()).toReturn(result);
		
		// When
		runner.run(step);
		
		// Then
		verify(step).perform();
		verify(result).describeTo(reporter);
	}
	
	@Test
	public void shouldNotPerformStepsAfterStepsWhichShouldNotContinue() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step firstStepNormal = mock(Step.class);
		Step secondStepPending = mock(Step.class);
		Step thirdStepNormal = mock(Step.class);
		Step fourthStepAlsoPending = mock(Step.class);

		StepResult success = mock(StepResult.class);
		StepResult pending = mock(StepResult.class);
		StepResult notRun = mock(StepResult.class);
		StepResult alsoPending = mock(StepResult.class);
		
		stub(success.shouldContinue()).toReturn(true);
		stub(pending.shouldContinue()).toReturn(false);
		
		ScenarioRunner runner = new ScenarioRunner(reporter);
		
		stub(firstStepNormal.perform()).toReturn(success);
		stub(secondStepPending.perform()).toReturn(pending);
		stub(thirdStepNormal.doNotPerform()).toReturn(notRun);
		stub(fourthStepAlsoPending.doNotPerform()).toReturn(alsoPending);
		
		// When
		runner.run(firstStepNormal, secondStepPending, thirdStepNormal, fourthStepAlsoPending);
		
		// Then
		verify(firstStepNormal).perform();
		verify(secondStepPending).perform();
		verify(thirdStepNormal).doNotPerform();
		verify(fourthStepAlsoPending).doNotPerform();
		
		verify(success).describeTo(reporter);
		verify(pending).describeTo(reporter);
		verify(notRun).describeTo(reporter);
		verify(alsoPending).describeTo(reporter);
	}
	
	@Test
	public void shouldRethrowAnyThrowablesAfterScenarioIsFinished() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step firstStepExceptional = mock(Step.class);
		Step secondStepNotPerformed = mock(Step.class);
		
		StepResult exceptionalResult = mock(StepResult.class);
		StepResult notRunResult = mock(StepResult.class);
		
		stub(firstStepExceptional.perform()).toReturn(exceptionalResult);
		stub(secondStepNotPerformed.doNotPerform()).toReturn(notRunResult);
		IllegalStateException exception = new IllegalStateException();
		stub(exceptionalResult.getThrowable()).toReturn(exception);
		stub(exceptionalResult.shouldContinue()).toReturn(false);
		
		ScenarioRunner runner = new ScenarioRunner(reporter);
		
		// When
		try {
			runner.run(firstStepExceptional, secondStepNotPerformed);
			fail("Should have rethrown exception");
		} catch (IllegalStateException e) {
			ensureThat(e, equalTo(exception));
		}
		
		// Then scenario should still have finished
		verify(firstStepExceptional).perform();
		verify(exceptionalResult).describeTo(reporter);
		verify(secondStepNotPerformed).doNotPerform();
		verify(notRunResult).describeTo(reporter);
		
	}
	
	@Test
	public void shouldResetStateForEachSetOfSteps() throws Throwable {

		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		Step secondStep = mock(Step.class);
		stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
		stub(secondStep.perform()).toReturn(StepResult.success("secondStep"));
		
		ScenarioRunner runner = new ScenarioRunner(reporter);
		
		runner.run(pendingStep);
		runner.run(secondStep); // should reset state for this one
		
		verify(pendingStep).perform();
		verify(secondStep).perform();
		verify(secondStep, never()).doNotPerform();
	}
	
}

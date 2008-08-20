package org.jbehave.scenario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.inOrder;

import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepResult;
import org.junit.Test;
import org.mockito.InOrder;

public class ScenarioRunnerBehaviour {

	@Test
	public void shouldPerformStepsThenDescribeThemToReporter() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step step = mock(Step.class);
		StepResult result = mock(StepResult.class);
		ScenarioRunner runner = new ScenarioRunner(reporter, PendingStepStrategy.PASSING);
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

		ScenarioRunner runner = new ScenarioRunner(reporter, PendingStepStrategy.PASSING);
		
		stub(firstStepNormal.perform()).toReturn(StepResult.success("Given I succeed"));
		stub(secondStepPending.perform()).toReturn(StepResult.pending("When I am pending"));
		stub(thirdStepNormal.doNotPerform()).toReturn(StepResult.notPerformed("Then I should not be performed"));
		stub(fourthStepAlsoPending.doNotPerform()).toReturn(StepResult.notPerformed("Then I should not be performed either"));
		
		// When
		runner.run(firstStepNormal, secondStepPending, thirdStepNormal, fourthStepAlsoPending);
		
		// Then
		verify(firstStepNormal).perform();
		verify(secondStepPending).perform();
		verify(thirdStepNormal).doNotPerform();
		verify(fourthStepAlsoPending).doNotPerform();
		
		verify(reporter).successful("Given I succeed");
		verify(reporter).pending("When I am pending");
		verify(reporter).notPerformed("Then I should not be performed");
		verify(reporter).notPerformed("Then I should not be performed either");
	}
	
	@Test
	public void shouldRethrowAnyThrowablesAfterScenarioIsFinished() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step firstStepExceptional = mock(Step.class);
		Step secondStepNotPerformed = mock(Step.class);
		
		StepResult failure = StepResult.failure("When I fail", new IllegalStateException());
		StepResult notPerformed = StepResult.notPerformed("Then I should not be performed");
		stub(firstStepExceptional.perform()).toReturn(failure);
		stub(secondStepNotPerformed.doNotPerform()).toReturn(notPerformed);
		
		ScenarioRunner runner = new ScenarioRunner(reporter, PendingStepStrategy.PASSING);
		
		// When
		try {
			runner.run(firstStepExceptional, secondStepNotPerformed);
			fail("Should have rethrown exception");
		} catch (IllegalStateException e) {
			ensureThat(e, equalTo(failure.getThrowable()));
		}
		
		// Then scenario should still have finished
		verify(firstStepExceptional).perform();
		verify(secondStepNotPerformed).doNotPerform();

		// And results should have been described
		verify(reporter).failed("When I fail", failure.getThrowable());
		verify(reporter).notPerformed("Then I should not be performed");
	}
	
	@Test
	public void shouldResetStateForEachSetOfSteps() throws Throwable {

		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		Step secondStep = mock(Step.class);
		stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
		stub(secondStep.perform()).toReturn(StepResult.success("secondStep"));
		
		ScenarioRunner runner = new ScenarioRunner(reporter, PendingStepStrategy.PASSING);
		
		runner.run(pendingStep);
		runner.run(secondStep); // should reset state for this one
		
		verify(pendingStep).perform();
		verify(secondStep).perform();
		verify(secondStep, never()).doNotPerform();
	}
	
	@Test
	public void shouldInformReporterOfChangeInScenarioForEachSetOfSteps() throws Throwable {
		IllegalArgumentException anException = new IllegalArgumentException();

		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		Step secondStep = mock(Step.class);
		Step failingStep = mock(Step.class);
		stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
		stub(secondStep.perform()).toReturn(StepResult.success("secondStep"));
		stub(failingStep.perform()).toReturn(StepResult.failure("failingStep", anException));
		
		ScenarioRunner runner = new ScenarioRunner(reporter, PendingStepStrategy.PASSING);
		
		runner.run("header for pending scenario", pendingStep);
		runner.run("header for second scenario", secondStep);
		try {
			runner.run("header for failing scenario", failingStep);
		} catch (IllegalArgumentException e) {
			// expected
		}
		
		InOrder inOrder = inOrder(reporter);
		
		inOrder.verify(reporter).beforeScenario("header for pending scenario");
		inOrder.verify(reporter).pending("pendingStep");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).beforeScenario("header for second scenario");
		inOrder.verify(reporter).successful("secondStep");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).beforeScenario("header for failing scenario");
		inOrder.verify(reporter).failed("failingStep", anException);
		inOrder.verify(reporter).afterScenario();
	}
	
	@Test
	public void shouldHandlePendingStepsAccordingToStrategy() throws Throwable {
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		StepResult pendingResult = StepResult.pending("My step isn't defined!");
		stub(pendingStep.perform()).toReturn(pendingResult);
		
		PendingStepStrategy strategy = mock(PendingStepStrategy.class);
		
		new ScenarioRunner(reporter, strategy).run(pendingStep);
		
		verify(strategy).handleError(pendingResult.getThrowable());
	}
	
}

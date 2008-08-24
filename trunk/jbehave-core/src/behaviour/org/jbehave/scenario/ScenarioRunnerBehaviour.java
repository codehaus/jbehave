package org.jbehave.scenario;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import org.jbehave.Configuration;
import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.ErrorStrategyInWhichWeTrustTheReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.CandidateStep;
import org.jbehave.scenario.steps.PendingStepStrategy;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.StepResult;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;
import org.mockito.InOrder;

public class ScenarioRunnerBehaviour {
	
	@Test
	public void shouldRunStepsInScenariosAndReportResultsToReporter() throws Throwable {
		ScenarioDefinition scenarioDefinition1 = new ScenarioDefinition("my title 1", "failingStep", "successfulStep");
		ScenarioDefinition scenarioDefinition2 = new ScenarioDefinition("my title 2", "successfulStep");
		ScenarioDefinition scenarioDefinition3 = new ScenarioDefinition("my title 3", "successfulStep", "pendingStep");
		StoryDefinition storyDefinition = new StoryDefinition(new Blurb("my blurb"), scenarioDefinition1, scenarioDefinition2, scenarioDefinition3);
		
		// Given
		CandidateStep[] someCandidateSteps = new CandidateStep[0];
		Step step = mock(Step.class);
		StepResult result = mock(StepResult.class);
		stub(step.perform()).toReturn(result);

		ScenarioReporter reporter = mock(ScenarioReporter.class);
		StepCreator creator = mock(StepCreator.class);
		Steps mySteps = mock(Steps.class);
		
		stub(mySteps.getSteps()).toReturn(someCandidateSteps);
		IllegalArgumentException anException = new IllegalArgumentException();

		Step pendingStep = mock(Step.class);
		Step successfulStep = mock(Step.class);
		Step failingStep = mock(Step.class);
		stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
		stub(successfulStep.perform()).toReturn(StepResult.success("successfulStep"));
		stub(successfulStep.doNotPerform()).toReturn(StepResult.notPerformed("successfulStep"));
		stub(failingStep.perform()).toReturn(StepResult.failure("failingStep", anException));

		stub(creator.createStepsFrom(scenarioDefinition1, mySteps)).toReturn(new Step[] {failingStep, successfulStep});
		stub(creator.createStepsFrom(scenarioDefinition2, mySteps)).toReturn(new Step[] {successfulStep});
		stub(creator.createStepsFrom(scenarioDefinition3, mySteps)).toReturn(new Step[] {successfulStep, pendingStep});

		ErrorStrategy errorStrategy = mock(ErrorStrategy.class);
		ScenarioRunner runner = new ScenarioRunner();
		
		runner.run(storyDefinition, configurationWith(reporter, creator, errorStrategy), mySteps);
		
		InOrder inOrder = inOrder(reporter, errorStrategy);
		inOrder.verify(reporter).beforeStory(storyDefinition.getBlurb());
		inOrder.verify(reporter).beforeScenario("my title 1");
		inOrder.verify(reporter).failed("failingStep", anException);
		inOrder.verify(reporter).notPerformed("successfulStep");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).beforeScenario("my title 2");
		inOrder.verify(reporter).successful("successfulStep");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).beforeScenario("my title 3");
		inOrder.verify(reporter).successful("successfulStep");
		inOrder.verify(reporter).pending("pendingStep");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).afterStory();
		inOrder.verify(errorStrategy).handleError(anException);
	}
	
	@Test
	public void shouldNotPerformStepsAfterStepsWhichShouldNotContinue() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step firstStepNormal = mock(Step.class);
		Step secondStepPending = mock(Step.class);
		Step thirdStepNormal = mock(Step.class);
		Step fourthStepAlsoPending = mock(Step.class);
		
		StepCreator creator = mock(StepCreator.class);
		Steps mySteps = mock(Steps.class);
		
		stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(mySteps)))
				.toReturn(new Step[] {firstStepNormal, secondStepPending, thirdStepNormal, fourthStepAlsoPending});
		
		ScenarioRunner runner = new ScenarioRunner();
		
		stub(firstStepNormal.perform()).toReturn(StepResult.success("Given I succeed"));
		stub(secondStepPending.perform()).toReturn(StepResult.pending("When I am pending"));
		stub(thirdStepNormal.doNotPerform()).toReturn(StepResult.notPerformed("Then I should not be performed"));
		stub(fourthStepAlsoPending.doNotPerform()).toReturn(StepResult.notPerformed("Then I should not be performed either"));

		// When
		runner.run(new StoryDefinition(new ScenarioDefinition("")), configurationWith(reporter, creator), mySteps);
		
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
	public void shouldReportAnyThrowablesThenHandleAfterStoryIsFinished() throws Throwable {
		// Given
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step firstStepExceptional = mock(Step.class);
		Step secondStepNotPerformed = mock(Step.class);
		
		StepResult failure = StepResult.failure("When I fail", new IllegalStateException());
		StepResult notPerformed = StepResult.notPerformed("Then I should not be performed");
		stub(firstStepExceptional.perform()).toReturn(failure);
		stub(secondStepNotPerformed.doNotPerform()).toReturn(notPerformed);

		StepCreator creator = mock(StepCreator.class);
		Steps mySteps = mock(Steps.class);
		ErrorStrategy errorStrategy = mock(ErrorStrategy.class);
		
		stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(mySteps)))
				.toReturn(new Step[] {firstStepExceptional, secondStepNotPerformed});
		
		ScenarioRunner runner = new ScenarioRunner();
		
		// When
		runner.run(new StoryDefinition(new ScenarioDefinition("")), configurationWith(reporter, creator, errorStrategy), mySteps);
		
		// Then scenario should still have finished
		verify(firstStepExceptional).perform();
		verify(secondStepNotPerformed).doNotPerform();

		// And results should have been described
		InOrder inOrder = inOrder(reporter, errorStrategy);
		inOrder.verify(reporter).beforeStory((Blurb)anyObject());
		inOrder.verify(reporter).beforeScenario((String)anyObject());
		inOrder.verify(reporter).failed("When I fail", failure.getThrowable());
		inOrder.verify(reporter).notPerformed("Then I should not be performed");
		inOrder.verify(reporter).afterScenario();
		inOrder.verify(reporter).afterStory();
		inOrder.verify(errorStrategy).handleError(failure.getThrowable());
	}
	
	@Test
	public void shouldResetStateForEachSetOfSteps() throws Throwable {

		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		Step secondStep = mock(Step.class);
		stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
		stub(secondStep.perform()).toReturn(StepResult.success("secondStep"));
		StepCreator creator = mock(StepCreator.class);
		Steps mySteps = mock(Steps.class);

		ScenarioDefinition scenario1 = mock(ScenarioDefinition.class);
		ScenarioDefinition scenario2 = mock(ScenarioDefinition.class);
		
		stub(creator.createStepsFrom(scenario1, mySteps))
				.toReturn(new Step[] {pendingStep});
		stub(creator.createStepsFrom(scenario2, mySteps))
			.toReturn(new Step[] {secondStep});
		
		ScenarioRunner runner = new ScenarioRunner();
		
		runner.run(new StoryDefinition(scenario1, scenario2), configurationWith(reporter, creator), mySteps);
		
		verify(pendingStep).perform();
		verify(secondStep).perform();
		verify(secondStep, never()).doNotPerform();
	}
	
	
	@Test
	public void shouldHandlePendingStepsAccordingToStrategy() throws Throwable {
		ScenarioReporter reporter = mock(ScenarioReporter.class);
		Step pendingStep = mock(Step.class);
		StepResult pendingResult = StepResult.pending("My step isn't defined!");
		stub(pendingStep.perform()).toReturn(pendingResult);
		
		PendingStepStrategy strategy = mock(PendingStepStrategy.class);
		
		StepCreator creator = mock(StepCreator.class);
		Steps mySteps = mock(Steps.class);
		
		stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(mySteps)))
				.toReturn(new Step[] {pendingStep});
		
		new ScenarioRunner().run(new StoryDefinition(new ScenarioDefinition("")), configurationWithPendingStrategy(creator, reporter, strategy), mySteps);
		
		verify(strategy).handleError(pendingResult.getThrowable());
	}

	
	private Configuration configurationWithPendingStrategy(StepCreator creator,
			ScenarioReporter reporter, PendingStepStrategy strategy) {
	
		return configurationWith(reporter, creator, new ErrorStrategyInWhichWeTrustTheReporter(), strategy);
	}

	private Configuration configurationWith(final ScenarioReporter reporter, final StepCreator creator) {
		return configurationWith(reporter, creator, new ErrorStrategyInWhichWeTrustTheReporter());
	}
	
	private Configuration configurationWith(final ScenarioReporter reporter,
			final StepCreator creator, final ErrorStrategy errorStrategy) {
		return configurationWith(reporter, creator, errorStrategy, PendingStepStrategy.PASSING);
	}
	
	private Configuration configurationWith(
			final ScenarioReporter reporter,
			final StepCreator creator,
			final ErrorStrategy errorStrategy,
			final PendingStepStrategy pendingStrategy) {
		
		return new PropertyBasedConfiguration() {
			@Override
			public StepCreator forCreatingSteps() { return creator; }
			@Override
			public ScenarioReporter forReportingScenarios() { return reporter; }
			@Override
			public ErrorStrategy forHandlingErrors() { return errorStrategy; }
			@Override
			public PendingStepStrategy forPendingSteps() { return pendingStrategy; }
		};
	}

	
}

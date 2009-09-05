package org.jbehave.scenario;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.errors.ErrorStrategy;
import org.jbehave.scenario.errors.ErrorStrategyInWhichWeTrustTheReporter;
import org.jbehave.scenario.errors.PendingErrorStrategy;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.CandidateStep;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepCreator;
import org.jbehave.scenario.steps.StepResult;
import org.jbehave.scenario.steps.Steps;
import org.junit.Test;
import org.mockito.InOrder;

public class ScenarioRunnerBehaviour {
    
    private Map<String, String> tableValues = new HashMap<String, String>();
    
	@Test
    public void shouldRunStepsInScenariosAndReportResultsToReporter() throws Throwable {
        ScenarioDefinition scenarioDefinition1 = new ScenarioDefinition("my title 1", asList("failingStep", "successfulStep"));
        ScenarioDefinition scenarioDefinition2 = new ScenarioDefinition("my title 2", asList("successfulStep"));
        ScenarioDefinition scenarioDefinition3 = new ScenarioDefinition("my title 3", asList("successfulStep", "pendingStep"));
        StoryDefinition storyDefinition = new StoryDefinition(new Blurb("my blurb"), scenarioDefinition1, scenarioDefinition2, scenarioDefinition3);
        
        // Given
        CandidateStep[] someCandidateSteps = new CandidateStep[0];
        Step step = mock(Step.class);
        StepResult result = mock(StepResult.class);
        stub(step.perform()).toReturn(result);

        ScenarioReporter reporter = mock(ScenarioReporter.class);
        StepCreator creator = mock(StepCreator.class);
        CandidateSteps mySteps = mock(Steps.class);
        
        stub(mySteps.getSteps()).toReturn(someCandidateSteps);
        IllegalArgumentException anException = new IllegalArgumentException();

        Step pendingStep = mock(Step.class);
        Step successfulStep = mock(Step.class);
        Step failingStep = mock(Step.class);
        stub(pendingStep.perform()).toReturn(StepResult.pending("pendingStep"));
        stub(successfulStep.perform()).toReturn(StepResult.success("successfulStep"));
        stub(successfulStep.doNotPerform()).toReturn(StepResult.notPerformed("successfulStep"));
        stub(failingStep.perform()).toReturn(StepResult.failure("failingStep", anException));

        stub(creator.createStepsFrom(scenarioDefinition1, tableValues, mySteps)).toReturn(new Step[] {failingStep, successfulStep});
        stub(creator.createStepsFrom(scenarioDefinition2, tableValues, mySteps)).toReturn(new Step[] {successfulStep});
        stub(creator.createStepsFrom(scenarioDefinition3, tableValues, mySteps)).toReturn(new Step[] {successfulStep, pendingStep});

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
    public void shouldRunGivenScenariosBeforeSteps() throws Throwable {
        ScenarioDefinition scenarioDefinition1 = new ScenarioDefinition("scenario 1", asList("successfulStep"));
        ScenarioDefinition scenarioDefinition2 = new ScenarioDefinition("scenario 2", asList("/path/to/given/scenario1"), asList("anotherSuccessfulStep"));
        StoryDefinition storyDefinition1 = new StoryDefinition(new Blurb("story 1"), scenarioDefinition1);
        StoryDefinition storyDefinition2 = new StoryDefinition(new Blurb("story 2"), scenarioDefinition2);
        
        // Given
        CandidateStep[] someCandidateSteps = new CandidateStep[0];
        Step step = mock(Step.class);
        StepResult result = mock(StepResult.class);
        stub(step.perform()).toReturn(result);

        ScenarioDefiner scenarioDefiner = mock(ScenarioDefiner.class);
        ScenarioReporter reporter = mock(ScenarioReporter.class);
        StepCreator creator = mock(StepCreator.class);
        CandidateSteps mySteps = mock(Steps.class);        
        stub(mySteps.getSteps()).toReturn(someCandidateSteps);
        Step successfulStep = mock(Step.class);
        stub(successfulStep.perform()).toReturn(StepResult.success("successfulStep"));
        Step anotherSuccessfulStep = mock(Step.class);
        stub(anotherSuccessfulStep.perform()).toReturn(StepResult.success("anotherSuccessfulStep"));

        stub(creator.createStepsFrom(scenarioDefinition1, tableValues, mySteps)).toReturn(new Step[] {successfulStep});
        stub(creator.createStepsFrom(scenarioDefinition2, tableValues, mySteps)).toReturn(new Step[] {anotherSuccessfulStep});
        
        stub(scenarioDefiner.loadScenarioDefinitionsFor("/path/to/given/scenario1")).toReturn(storyDefinition1);
        
        ErrorStrategy errorStrategy = mock(ErrorStrategy.class);

        ScenarioRunner runner = new ScenarioRunner();
        
        runner.run(storyDefinition2, configurationWith(scenarioDefiner, reporter, creator, errorStrategy), mySteps);
        
        InOrder inOrder = inOrder(reporter);
        inOrder.verify(reporter).beforeStory(storyDefinition2.getBlurb());
        inOrder.verify(reporter).givenScenario("/path/to/given/scenario1");
        inOrder.verify(reporter).successful("successfulStep");
        inOrder.verify(reporter).successful("anotherSuccessfulStep");
        inOrder.verify(reporter).afterStory();
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
        
        stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(tableValues), eq(mySteps)))
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
        
        stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(tableValues), eq(mySteps)))
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
        CandidateSteps mySteps = mock(Steps.class);

        ScenarioDefinition scenario1 = mock(ScenarioDefinition.class);
        ScenarioDefinition scenario2 = mock(ScenarioDefinition.class);

        stub(creator.createStepsFrom(scenario1, tableValues, mySteps))
                .toReturn(new Step[] {pendingStep});
        stub(creator.createStepsFrom(scenario2, tableValues, mySteps))
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
        
        PendingErrorStrategy strategy = mock(PendingErrorStrategy.class);
        
        StepCreator creator = mock(StepCreator.class);
        Steps mySteps = mock(Steps.class);
        
        stub(creator.createStepsFrom((ScenarioDefinition)anyObject(), eq(tableValues), eq(mySteps)))
                .toReturn(new Step[] {pendingStep});
        
        new ScenarioRunner().run(new StoryDefinition(new ScenarioDefinition("")), configurationWithPendingStrategy(creator, reporter, strategy), mySteps);
        
        verify(strategy).handleError(pendingResult.getThrowable());
    }
    
    private Configuration configurationWithPendingStrategy(StepCreator creator,
            ScenarioReporter reporter, PendingErrorStrategy strategy) {    
        return configurationWith(new ClasspathScenarioDefiner(), reporter, creator, new ErrorStrategyInWhichWeTrustTheReporter(), strategy);
    }

    private Configuration configurationWith(final ScenarioReporter reporter, final StepCreator creator) {
        return configurationWith(reporter, creator, new ErrorStrategyInWhichWeTrustTheReporter());
    }
    
    private Configuration configurationWith(ScenarioReporter reporter,
			StepCreator creator, ErrorStrategy errorStrategy) {
		return configurationWith(new ClasspathScenarioDefiner(), reporter, creator, errorStrategy);
	}

    private Configuration configurationWith(ScenarioDefiner definer,
            final ScenarioReporter reporter, final StepCreator creator, final ErrorStrategy errorStrategy) {
        return configurationWith(definer, reporter, creator, errorStrategy, PendingErrorStrategy.PASSING);
    }
    
    private Configuration configurationWith(
            final ScenarioDefiner definer, final ScenarioReporter reporter,
            final StepCreator creator,
            final ErrorStrategy errorStrategy,
            final PendingErrorStrategy pendingStrategy) {
        
        return new PropertyBasedConfiguration() {        	
            @Override
			public ScenarioDefiner forDefiningScenarios() { return definer; }
			@Override
            public StepCreator forCreatingSteps() { return creator; }
            @Override
            public ScenarioReporter forReportingScenarios() { return reporter; }
            @Override
            public ErrorStrategy forHandlingErrors() { return errorStrategy; }
            @Override
            public PendingErrorStrategy forPendingSteps() { return pendingStrategy; }
        };
    }

    
}

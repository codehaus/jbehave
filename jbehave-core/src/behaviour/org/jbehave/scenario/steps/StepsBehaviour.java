package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

import java.util.List;

import org.jbehave.scenario.annotations.AfterScenario;
import org.junit.Test;

public class StepsBehaviour {

    @Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedSteps() {
        MySteps steps = new MySteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(3));
        
        candidateSteps[0].createFrom("Given a given").perform();
        candidateSteps[1].createFrom("When a when").perform();
        candidateSteps[2].createFrom("Then a then").perform();

        ensureThat(steps.given);
        ensureThat(steps.when);
        ensureThat(steps.then);
    }
    
    @Test
    public void shouldProvideStepsToBePerformedBeforeScenarios() {
    	MySteps steps = new MySteps();
    	List<Step> executableSteps = steps.runBeforeScenario();
		ensureThat(executableSteps.size(), equalTo(1));
		
    	executableSteps.get(0).perform();
    	ensureThat(steps.before);
    }
    
    @Test
    public void shouldProvideStepsToBePerformedAfterScenarios() {
    	MySteps steps = new MySteps();
    	List<Step> executableSteps = steps.runAfterScenario();
    	ensureThat(executableSteps.size(), equalTo(3));
    	
    	executableSteps.get(0).perform();
    	ensureThat(steps.afterAll);
    	
    	executableSteps.get(1).perform();
    	ensureThat(steps.afterSuccess);
    	
    	executableSteps.get(2).doNotPerform();
    	ensureThat(steps.afterUnsuccess);
    }
    
    @Test
    public void shouldIgnoreSuccessfulStepsWhichArePerformedInUnsuccessfulScenarioOrViceVersa() {
    	MySteps steps = new MySteps();
    	List<Step> executableSteps = steps.runAfterScenario();
    	
    	executableSteps.get(0).doNotPerform();
    	ensureThat(steps.afterAll); // @AfterScenario can be run after all scenarios
    	
		executableSteps.get(1).doNotPerform();
		ensureThat(!steps.afterSuccess);
		
		
		executableSteps.get(2).perform();
		ensureThat(!steps.afterUnsuccess);
	
    }
    
    public static class MySteps extends Steps {
        
        private boolean given;
        private boolean when;
        private boolean then;
        
        private boolean before;
        private boolean afterAll;
        private boolean afterSuccess;
        private boolean afterUnsuccess;
        
        @org.jbehave.scenario.annotations.Given("a given")
        public void given() {
            given = true;
        }
        
        @org.jbehave.scenario.annotations.When("a when")
        public void when() {
            when = true;
        }
        
        @org.jbehave.scenario.annotations.Then("a then")
        public void then() {
            then = true;
        }
        
        @org.jbehave.scenario.annotations.BeforeScenario
        public void beforeScenarios() {
        	before = true;
        }
        
        @org.jbehave.scenario.annotations.AfterScenario
        public void afterAllScenarios() {
        	afterAll = true;
        }
        
        @org.jbehave.scenario.annotations.AfterScenario(uponOutcome=AfterScenario.Outcome.SUCCESS)
        public void afterSuccessfulScenarios() {
        	afterSuccess = true;
        }
        
        @org.jbehave.scenario.annotations.AfterScenario(uponOutcome=AfterScenario.Outcome.FAILURE)
        public void afterUnsuccessfulScenarios() {
        	afterUnsuccess = true;
        }
        
        
    }
}

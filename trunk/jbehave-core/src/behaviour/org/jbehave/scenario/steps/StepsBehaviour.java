package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.jbehave.scenario.annotations.AfterScenario;
import org.junit.Test;

public class StepsBehaviour {

    @Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedSteps() {
        MySteps steps = new MySteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(6));

        candidateSteps[0].createFrom("Given a given").perform();
        candidateSteps[1].createFrom("Given a given alias").perform();
        candidateSteps[2].createFrom("When a when").perform();
        candidateSteps[3].createFrom("When a when alias").perform();
        candidateSteps[4].createFrom("Then a then").perform();
        candidateSteps[5].createFrom("Then a then alias").perform();

        ensureThat(steps.givens, equalTo(2));
        ensureThat(steps.whens, equalTo(2));
        ensureThat(steps.thens, equalTo(2));
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
    	ensureThat(steps.afterAny);
    	
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
    	ensureThat(steps.afterAny); // @AfterScenario is run after scenarios of any outcome 
    	
		executableSteps.get(1).doNotPerform();
		ensureThat(!steps.afterSuccess); // @AfterScenario(uponOutcome=SUCCESS) is run after successful scenarios
		
		
		executableSteps.get(2).perform();
		ensureThat(!steps.afterUnsuccess); // @AfterScenario(uponOutcome=FAILURE) is run after unsuccessful scenarios
	
    }
    
    public static class MySteps extends Steps {
        
        private int givens;
        private int whens;
        private int thens;
        
        private boolean before;
        private boolean afterAny;
        private boolean afterSuccess;
        private boolean afterUnsuccess;
        
        @org.jbehave.scenario.annotations.Given("a given")
        @org.jbehave.scenario.annotations.Aliases(values={"a given alias"})
        public void given() {
            givens++;
        }
        
        @org.jbehave.scenario.annotations.When("a when")
        @org.jbehave.scenario.annotations.Aliases(values={"a when alias"})
        public void when() {
            whens++;
        }
        
        @org.jbehave.scenario.annotations.Then("a then")
        @org.jbehave.scenario.annotations.Aliases(values={"a then alias"})
        public void then() {
            thens++;
        }
        
        @org.jbehave.scenario.annotations.BeforeScenario
        public void beforeScenarios() {
        	before = true;
        }
        
        @org.jbehave.scenario.annotations.AfterScenario
        public void afterAnyScenarios() {
        	afterAny = true;
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

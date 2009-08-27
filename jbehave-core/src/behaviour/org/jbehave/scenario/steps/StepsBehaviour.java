package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.steps.Steps.DuplicateCandidateStepFoundException;
import org.junit.Test;

public class StepsBehaviour {

    @Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedStepsWithMultipleAliases() {
        MyStepsWithAliases steps = new MyStepsWithAliases();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(9));

        candidateSteps[0].createFrom("Given a given").perform();
        candidateSteps[1].createFrom("Given a given alias").perform();
        candidateSteps[2].createFrom("Given another given alias").perform();
        candidateSteps[3].createFrom("When a when").perform();
        candidateSteps[4].createFrom("When a when alias").perform();
        candidateSteps[5].createFrom("When another when alias").perform();
        candidateSteps[6].createFrom("Then a then").perform();
        candidateSteps[7].createFrom("Then a then alias").perform();
        candidateSteps[8].createFrom("Then another then alias").perform();

        ensureThat(steps.givens, equalTo(3));
        ensureThat(steps.whens, equalTo(3));
        ensureThat(steps.thens, equalTo(3));
    }

    @Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedStepsWithSingleAlias() {
        MyStepsWithAlias steps = new MyStepsWithAlias();
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
    	MyStepsWithAliases steps = new MyStepsWithAliases();
    	List<Step> executableSteps = steps.runBeforeScenario();
		ensureThat(executableSteps.size(), equalTo(1));
		
    	executableSteps.get(0).perform();
    	ensureThat(steps.before);
    }
    
    @Test
    public void shouldProvideStepsToBePerformedAfterScenarios() {
    	MyStepsWithAliases steps = new MyStepsWithAliases();
    	List<Step> executableSteps = steps.runAfterScenario();
    	ensureThat(executableSteps.size(), equalTo(3));
    	
    	executableSteps.get(0).perform();
    	ensureThat(steps.afterAny);
    	
    	executableSteps.get(1).perform();
    	ensureThat(steps.afterSuccess);
    	
    	executableSteps.get(2).doNotPerform();
    	ensureThat(steps.afterFailure);
    }
    
    @Test
    public void shouldIgnoreSuccessfulStepsWhichArePerformedInUnsuccessfulScenarioOrViceVersa() {
    	MyStepsWithAliases steps = new MyStepsWithAliases();
    	List<Step> executableSteps = steps.runAfterScenario();
    	
    	executableSteps.get(0).doNotPerform();
    	ensureThat(steps.afterAny); // @AfterScenario is run after scenarios of any outcome 
    	
		executableSteps.get(1).doNotPerform();
		ensureThat(!steps.afterSuccess); // @AfterScenario(uponOutcome=SUCCESS) is run after successful scenarios
		
		
		executableSteps.get(2).perform();
		ensureThat(!steps.afterFailure); // @AfterScenario(uponOutcome=FAILURE) is run after unsuccessful scenarios
	
    }


    @Test(expected=DuplicateCandidateStepFoundException.class)
    public void shouldFailIfDuplicateStepsAreEncountered() {
        DuplicateSteps steps = new DuplicateSteps();
        CandidateStep[] candidateSteps = steps.getSteps();

        ensureThat(candidateSteps.length, equalTo(2));
        candidateSteps[0].createFrom("Given a given").perform();

    }

    
    public static class MyStepsWithAliases extends Steps {
        
        private int givens;
        private int whens;
        private int thens;
        
        private boolean before;
        private boolean afterAny;
        private boolean afterSuccess;
        private boolean afterFailure;
        
        @org.jbehave.scenario.annotations.Given("a given")
        @org.jbehave.scenario.annotations.Aliases(values={"a given alias", "another given alias"})
        public void given() {
            givens++;
        }

        @org.jbehave.scenario.annotations.When("a when")
        @org.jbehave.scenario.annotations.Aliases(values={"a when alias", "another when alias"})
        public void when() {
            whens++;
        }
        
        @org.jbehave.scenario.annotations.Then("a then")
        @org.jbehave.scenario.annotations.Aliases(values={"a then alias", "another then alias"})
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
        	afterFailure = true;
        }
        
        
    }

    public static class MyStepsWithAlias extends Steps {

        private int givens;
        private int whens;
        private int thens;

        private boolean before;
        private boolean afterAny;
        private boolean afterSuccess;
        private boolean afterFailure;

        @org.jbehave.scenario.annotations.Given("a given")
        @org.jbehave.scenario.annotations.Alias("a given alias")
        public void given() {
            givens++;
        }

        @org.jbehave.scenario.annotations.When("a when")
        @org.jbehave.scenario.annotations.Alias("a when alias")
        public void when() {
            whens++;
        }

        @org.jbehave.scenario.annotations.Then("a then")
        @org.jbehave.scenario.annotations.Alias("a then alias")
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
        	afterFailure = true;
        }


    }



    public static class DuplicateSteps extends Steps {
        
        @org.jbehave.scenario.annotations.Given("a given")
        public void given() {
        }

        @org.jbehave.scenario.annotations.Given("a given")
        public void duplicateGiven() {
        }
                
    }
}

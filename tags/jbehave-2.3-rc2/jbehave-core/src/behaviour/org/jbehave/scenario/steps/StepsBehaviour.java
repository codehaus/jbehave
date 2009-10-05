package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.i18n.I18nKeyWords;
import org.jbehave.scenario.steps.CandidateStep.StartingWordNotFound;
import org.jbehave.scenario.steps.Steps.DuplicateCandidateStepFoundException;
import org.junit.Test;

public class StepsBehaviour {

    private Map<String, String> tableRow = new HashMap<String, String>();

	@Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedStepsWithMultipleAliases() {
        MultipleAliasesSteps steps = new MultipleAliasesSteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(9));

        candidateSteps[0].createFrom(tableRow, "Given a given").perform();
        candidateSteps[1].createFrom(tableRow, "Given a given alias").perform();
        candidateSteps[2].createFrom(tableRow, "Given another given alias").perform();
        candidateSteps[3].createFrom(tableRow, "When a when").perform();
        candidateSteps[4].createFrom(tableRow, "When a when alias").perform();
        candidateSteps[5].createFrom(tableRow, "When another when alias").perform();
        candidateSteps[6].createFrom(tableRow, "Then a then").perform();
        candidateSteps[7].createFrom(tableRow, "Then a then alias").perform();
        candidateSteps[8].createFrom(tableRow, "Then another then alias").perform();

        ensureThat(steps.givens, equalTo(3));
        ensureThat(steps.whens, equalTo(3));
        ensureThat(steps.thens, equalTo(3));
    }

    @Test
    public void shouldProvideCandidateStepsCorrespondingToAnnotatedStepsWithSingleAlias() {
        SingleAliasSteps steps = new SingleAliasSteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(6));

        candidateSteps[0].createFrom(tableRow, "Given a given").perform();
        candidateSteps[1].createFrom(tableRow, "Given a given alias").perform();
        candidateSteps[2].createFrom(tableRow, "When a when").perform();
        candidateSteps[3].createFrom(tableRow, "When a when alias").perform();
        candidateSteps[4].createFrom(tableRow, "Then a then").perform();
        candidateSteps[5].createFrom(tableRow, "Then a then alias").perform();

        ensureThat(steps.givens, equalTo(2));
        ensureThat(steps.whens, equalTo(2));
        ensureThat(steps.thens, equalTo(2));
    }

    @Test
    public void shouldProvideStepsToBePerformedBeforeScenarios() {
    	MultipleAliasesSteps steps = new MultipleAliasesSteps();
    	List<Step> executableSteps = steps.runBeforeScenario();
		ensureThat(executableSteps.size(), equalTo(1));
		
    	executableSteps.get(0).perform();
    	ensureThat(steps.before);
    }
    
    @Test
    public void shouldProvideStepsToBePerformedAfterScenarios() {
    	MultipleAliasesSteps steps = new MultipleAliasesSteps();
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
    	MultipleAliasesSteps steps = new MultipleAliasesSteps();
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
        candidateSteps[0].createFrom(tableRow, "Given a given").perform();

    }

    @Test
    public void shouldAllowI18nOfSteps(){
    	I18nSteps steps = new I18nSteps(new I18nKeyWords(new Locale("it")));
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(3));

        candidateSteps[0].createFrom(tableRow, "Dato che un dato che").perform();
        candidateSteps[1].createFrom(tableRow, "Quando un quando").perform();
        candidateSteps[2].createFrom(tableRow, "Allora un allora").perform();

        ensureThat(steps.givens, equalTo(1));
        ensureThat(steps.whens, equalTo(1));
        ensureThat(steps.thens, equalTo(1));    	    	
    }

    @Test(expected=StartingWordNotFound.class)
    public void shouldNotCreateStepIfStartingWordNotFound(){
    	I18nSteps steps = new I18nSteps(new I18nKeyWords(new Locale("it")));
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(3));

        // misspelled starting word 
        candidateSteps[0].createFrom(tableRow, "Dado che un dato che"); 
        
    }
    
    static class MultipleAliasesSteps extends Steps {
        
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

    static class SingleAliasSteps extends Steps {

        private int givens;
        private int whens;
        private int thens;

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

    }

    static class DuplicateSteps extends Steps {
        
        @org.jbehave.scenario.annotations.Given("a given")
        public void given() {
        }

        @org.jbehave.scenario.annotations.Given("a given")
        public void duplicateGiven() {
        }
                
    }

    static class I18nSteps extends Steps {

        private int givens;
        private int whens;
        private int thens;

        public I18nSteps(I18nKeyWords keywords) {
        	super(keywords);
		}

		@org.jbehave.scenario.annotations.Given("un dato che")
        public void given() {
            givens++;
        }

        @org.jbehave.scenario.annotations.When("un quando")
        public void when() {
            whens++;
        }

        @org.jbehave.scenario.annotations.Then("un allora")
        public void then() {
            thens++;
        }

    }
}

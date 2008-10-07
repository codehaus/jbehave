package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

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
    
    public static class MySteps extends Steps {
        
        private boolean given;
        private boolean when;
        private boolean then;

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
    }
}

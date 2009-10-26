package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.CandidateStep.StartingWordNotFound;
import org.junit.Test;

public class ConfusingStepsBehavior {

    private Map<String, String> tableRow = new HashMap<String, String>();

	@Test
    public void shouldAllowStepOfDifferentTypesWithPatternThatMatchesSameStep() {
        MySteps steps = new MySteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(2));
        candidateSteps[0].createFrom(tableRow, "Given foo named xyz").perform();
        candidateSteps[1].createFrom(tableRow, "When foo named Bar is created").perform();
        ensureThat(steps.givenName, equalTo("xyz"));
        ensureThat(steps.whenName, equalTo("Bar"));
    }

	@Test(expected=StartingWordNotFound.class)
    public void shouldFailWhenTryingToMatchStepOfWrongType() {
        MySteps steps = new MySteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(2));
        candidateSteps[0].createFrom(tableRow, "Given foo named xyz").perform();
        ensureThat(steps.givenName, equalTo("xyz"));
        candidateSteps[0].createFrom(tableRow, "Then foo named xyz").perform();
    }
	
    static class MySteps extends Steps {
        private String givenName;
        private String whenName;

        @Given("foo named $name")
        public void givenFoo(String name) {
        	givenName = name;
        }

        @When("foo named $name is created")
        public void createFoo(String name) {
        	whenName = name;
        }

    }


}

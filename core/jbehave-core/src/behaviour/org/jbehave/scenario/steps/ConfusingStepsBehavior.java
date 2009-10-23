package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.When;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConfusingStepsBehavior {

    private Map<String, String> tableRow = new HashMap<String, String>();

	@Test
    public void shouldBeAbleToDisambiguateSimilarSteps() {
        MySteps steps = new MySteps();
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(2));
        candidateSteps[0].createFrom(tableRow, "Given foo named xyz").perform();
        candidateSteps[1].createFrom(tableRow, "When foo named Bar is created").perform();
        ensureThat(steps.given, equalTo(true));
        ensureThat(steps.when, equalTo(true));
    }

    static class MySteps extends Steps {
        private boolean when;
        private boolean given;

        @Given("foo named $name")
        public void givenFoo(String name) {
            given = true;
        }

        @When("foo named $name is created")
        public void createFoo(String name) {
            when = true;
        }

    }


}

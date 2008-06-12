package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.jbehave.Ensure.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jbehave.scenario.ScenarioReporter;
import org.junit.Test;

public class CandidateStepBehaviour {

    private static final StepPatternBuilder PATTERN_BUILDER = new DollarStepPatternBuilder();
    private static final StepMonitor MONITOR = new SilentStepMonitor();
    private static final String NL = System.getProperty("line.separator");

    @Test
    public void shouldMatchASimpleString() throws Exception {
        CandidateStep candidateStep = new Given("I laugh", SomeSteps.class.getMethod("aMethod"), null,
                PATTERN_BUILDER, MONITOR);

        ensureThat(candidateStep.matches("Given I laugh"));
        ensureThat(not(candidateStep.matches("When I laugh")));
        ensureThat(not(candidateStep.matches("Then I laugh")));
    }

    @Test
    public void shouldMatchAStringWithArguments() throws Exception {
        CandidateStep candidateStep = new When("windows on the $nth floor", SomeSteps.class.getMethod("aMethod"), null,
                PATTERN_BUILDER, MONITOR);
        ensureThat(candidateStep.matches("When windows on the 1st floor"));
        ensureThat(not(candidateStep.matches("When windows on the 1st floor are open")));
    }

    @Test
    public void shouldProvideARealStepUsingTheMatchedString() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new Then("I live on the $nth floor", SomeSteps.class.getMethod("aMethodWith",
                String.class), someSteps, PATTERN_BUILDER, MONITOR);
        Step step = candidateStep.createFrom("Then I live on the 1st floor");
        step.perform();
        ensureThat((String) someSteps.args, equalTo("1st"));
    }

    @Test
    public void shouldMatchMultilineStrings() throws Exception {
        CandidateStep candidateStep = new Then("the grid should look like $grid", SomeSteps.class.getMethod("aMethod"),
                null, PATTERN_BUILDER, MONITOR);
        ensureThat(candidateStep.matches("Then the grid should look like " + NL + "...." + NL + "...." + NL));
    }

    @Test
    public void shouldConvertArgsToAppropriateNumbers() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new Then("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith",
                int.class), someSteps, PATTERN_BUILDER, MONITOR);
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Integer) someSteps.args, equalTo(14));

        candidateStep = new Then("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith", long.class),
                someSteps, PATTERN_BUILDER, MONITOR);
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Long) someSteps.args, equalTo(14L));

        candidateStep = new Then("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith", double.class),
                someSteps, PATTERN_BUILDER, MONITOR);
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Double) someSteps.args, equalTo(14.0));

        candidateStep = new Then("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith", float.class),
                someSteps, PATTERN_BUILDER, MONITOR);
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Float) someSteps.args, equalTo(14.0f));
    }

    @Test
    public void shouldProvideAStepWithADescriptionThatMatchesTheCandidateStep() throws Exception {
        ScenarioReporter reporter = mock(ScenarioReporter.class);
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new Then("I live on the $nth floor", SomeSteps.class.getMethod("aMethodWith",
                String.class), someSteps, PATTERN_BUILDER, MONITOR);
        Step step = candidateStep.createFrom("Then I live on the 1st floor");

        StepResult result = step.perform();
        result.describeTo(reporter);

        verify(reporter).successful("Then I live on the 1st floor");
    }

    public class SomeSteps extends Steps {
        private Object args;

        public void aMethod() {

        }

        public void aMethodWith(String args) {
            this.args = args;
        }

        public void aMethodWith(double args) {
            this.args = args;
        }

        public void aMethodWith(long args) {
            this.args = args;
        }

        public void aMethodWith(int args) {
            this.args = args;
        }

        public void aMethodWith(float args) {
            this.args = args;
        }
    }
}

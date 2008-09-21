package org.jbehave.container.pico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

import org.jbehave.container.AComponent;
import org.jbehave.container.AnotherComponent;
import org.jbehave.scenario.steps.CandidateStep;
import org.junit.Test;

public class XMLPicoContainerStepsBehaviour {

    @Test
    public void shouldProvideCandidateStepsThatAreContainerAware() {
        MySteps steps = new MySteps("org/jbehave/container/pico/components.xml");
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(3));
        
        candidateSteps[0].createFrom("Given a component").perform();
        candidateSteps[1].createFrom("When another component").perform();
        candidateSteps[2].createFrom("Then say hurray").perform();

        ensureThat(steps.aComponent != null);
        ensureThat(steps.anotherComponent != null);
        ensureThat(steps.sayHurray);
        
    }

    public static class MySteps extends XMLPicoContainerSteps {
        
        public boolean sayHurray;
        public Object aComponent;
        public Object anotherComponent;

        public MySteps(String containerResource) {
            super(containerResource);
        }

        @org.jbehave.scenario.annotations.Given("a component")
        public void given() {
            aComponent = component(AComponent.class);
        }

        @org.jbehave.scenario.annotations.When("another component")
        public void when() {
            anotherComponent = component(AnotherComponent.class);
        }

        @org.jbehave.scenario.annotations.Then("say hurray")
        public void then() {
            sayHurray = true;
        }
    }
}

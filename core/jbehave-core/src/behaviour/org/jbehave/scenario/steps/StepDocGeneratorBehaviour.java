package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.junit.Test;

public class StepDocGeneratorBehaviour {
	
    @Test
    public void shouldGenerateStepDocsInPriorityOrder() {
        StepDocGenerator generator = new DefaultStepDocGenerator();
        MySteps steps = new MySteps();
        List<StepDoc> stepdocs = generator.generate(steps.getClass());
        ensureThat(stepdocs.get(0).getPattern(), equalTo("a given"));
        ensureThat(stepdocs.get(0).getAliasPatterns(), equalTo(asList("a given alias", "another given alias")));
        ensureThat(stepdocs.get(1).getPattern(), equalTo("a when"));
        ensureThat(stepdocs.get(1).getAliasPatterns(), equalTo(asList("a when alias", "another when alias")));
        ensureThat(stepdocs.get(2).getPattern(), equalTo("a then"));
        ensureThat(stepdocs.get(2).getAliasPatterns(), equalTo(asList("a then alias", "another then alias")));
    }    
    
    public static class MySteps extends Steps {
        
        private int givens;
        private int whens;
        private int thens;
        
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
                
    }
    
}

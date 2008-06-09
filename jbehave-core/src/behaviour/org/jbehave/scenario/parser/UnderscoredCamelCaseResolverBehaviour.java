package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.scenario.Scenario;
import org.junit.Test;

public class UnderscoredCamelCaseResolverBehaviour {

    @Test
    public void shouldResolveCamelCasedClassNameToUnderscoredName() {
        UnderscoredCamelCaseResolver converter = new UnderscoredCamelCaseResolver();
        ensureThat(converter.resolve(CamelCaseScenario.class),
                equalTo("org/jbehave/scenario/parser/camel_case_scenario"));

    }
    
    static class CamelCaseScenario extends Scenario {
        
    }
}

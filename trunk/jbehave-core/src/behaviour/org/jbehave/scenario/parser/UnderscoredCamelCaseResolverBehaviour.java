package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.scenario.JUnitScenario;
import org.junit.Test;

public class UnderscoredCamelCaseResolverBehaviour {

    @Test
    public void shouldResolveCamelCasedClassNameToUnderscoredName() {
        UnderscoredCamelCaseResolver resolver = new UnderscoredCamelCaseResolver();
        ensureThat(resolver.resolve(CamelCaseScenario.class),
                equalTo("org/jbehave/scenario/parser/camel_case_scenario"));

    }
    
    
    @Test
    public void shouldResolveCamelCasedClassNameToUnderscoredNameWithExtension() {
        UnderscoredCamelCaseResolver resolver = new UnderscoredCamelCaseResolver(".scenario");
        ensureThat(resolver.resolve(CamelCase.class),
                equalTo("org/jbehave/scenario/parser/camel_case.scenario"));

    }
    
    @Test
    public void shouldResolveCamelCasedClassNameWithNumbers() {
        UnderscoredCamelCaseResolver resolver = new UnderscoredCamelCaseResolver();
        ensureThat(resolver.resolve(CamelCaseWith3Dates.class),
                equalTo("org/jbehave/scenario/parser/camel_case_with_3_dates"));

    }
    
    static class CamelCaseScenario extends JUnitScenario {
        
    }

    static class CamelCase extends JUnitScenario {
        
    }
    
    static class CamelCaseWith3Dates extends JUnitScenario {
        
    }
}

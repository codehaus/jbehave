package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

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
    
    static class CamelCaseScenario extends JUnitScenario {
        
    }

    static class CamelCase extends JUnitScenario {
        
    }
}

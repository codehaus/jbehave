package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.scenario.Scenario;
import org.junit.Test;

public class CasePreservingResolverBehaviour {

    @Test
    public void shouldResolveClassNamePreservingCase() {
        CasePreservingResolver resolver = new CasePreservingResolver(".scenario");
        ensureThat(resolver.resolve(CamelCase.class),
                equalTo("org/jbehave/scenario/parser/CamelCase.scenario"));

    }
    
    static class CamelCase extends Scenario {
        
    }
}
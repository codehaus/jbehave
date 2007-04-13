package org.jbehave.core.matchers;

import org.jbehave.core.Ensure;
import org.jbehave.core.matchers.UsingLogicalMatchers;
import org.jbehave.core.mock.UsingMatchers;

public class UsingLogicalMatchersBehaviour {
    
    public void shouldProvideConditionalMatchers() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        String horse = "horse";
        String cow = "cow";
        
        Ensure.that(horse, UsingLogicalMatchers.or(m.eq(horse), m.eq(cow)));
        Ensure.that(cow, UsingLogicalMatchers.either(m.eq(horse), m.eq(cow)));
        
        Ensure.that(horse, UsingLogicalMatchers.and(m.eq(horse), m.contains("ors")));
        Ensure.that(cow, UsingLogicalMatchers.both(m.eq(cow), m.endsWith("ow")));
    }
}

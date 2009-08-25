package org.jbehave.core.matchers;

import org.jbehave.core.Ensure;

public class UsingEqualityMatchersBehaviour {
    
    public void shouldProvideMatchersForDoubles() {
        Ensure.that(5.0, UsingEqualityMatchers.eq(5.0));
        Ensure.that(5.0, UsingLogicalMatchers.not(UsingEqualityMatchers.eq(5.1)));
        Ensure.that(5.0, UsingEqualityMatchers.eq(5.0), "message");
    }
    
    public void shouldProvideMatchersForLongsAndInts() {
        Ensure.that(5, UsingEqualityMatchers.eq(5));
        Ensure.that(5, UsingLogicalMatchers.not(UsingEqualityMatchers.eq(4)));
        Ensure.that(5, UsingEqualityMatchers.eq(5), "message");
    }
    
    public void shouldProvideMatchersForChars() {
        Ensure.that('a', UsingEqualityMatchers.eq('a'));
        Ensure.that('a', UsingLogicalMatchers.not(UsingEqualityMatchers.eq('A')));
        Ensure.that('a', UsingEqualityMatchers.eq('a'), "message");
    }
    
    public void shouldProvideMatchersForBooleans() {
        Ensure.that(true, UsingEqualityMatchers.eq(true));
        Ensure.that(true, UsingLogicalMatchers.not(UsingEqualityMatchers.eq(false)));
        Ensure.that(true, UsingEqualityMatchers.eq(true), "message");
    }
    
    public void shouldProvideMatchersToCheckForNull() {
        Ensure.that(null, UsingEqualityMatchers.isNull());
        Ensure.that(new Object(), UsingLogicalMatchers.not(UsingEqualityMatchers.isNull()));
        Ensure.that(new Object(), UsingEqualityMatchers.isNotNull());
        Ensure.that(null, UsingLogicalMatchers.not(UsingEqualityMatchers.isNotNull()));
    }
    
    public void shouldProvideMatchersToCheckForAnything() {
        Ensure.that(null, UsingLogicalMatchers.not(UsingEqualityMatchers.nothing()));
        Ensure.that(new Object(), UsingLogicalMatchers.not(UsingEqualityMatchers.nothing()));
        Ensure.that(new Object(), UsingEqualityMatchers.anything());
        Ensure.that(null, UsingEqualityMatchers.anything());
    }
    
    
    public void shouldProvideInstanceMatchers() {
        String a = "a";
        String b = "b";
        
        Ensure.that(a, UsingEqualityMatchers.is(a));
        Ensure.that(a, UsingLogicalMatchers.not(UsingEqualityMatchers.is(b)));
        Ensure.that(a, UsingEqualityMatchers.sameInstanceAs(a));
        Ensure.that(a, UsingLogicalMatchers.not(UsingEqualityMatchers.sameInstanceAs(b)));
    }
}

package jbehave.core;

import jbehave.core.mock.UsingConstraints;

public class UsingConstraintsBehaviour extends UsingConstraints {

    public void shouldProvideConstraintsForDoubles() {
        ensureThat(5.0, eq(5.0));
        ensureThat(5.0, not(eq(5.1)));
        ensureThat(5.0, eq(5.0), "message");
    }
    
    public void shouldProvideConstraintsForLongsAndInts() {
        ensureThat(5, eq(5));
        ensureThat(5, not(eq(4)));
        ensureThat(5, eq(5), "message");
    }
    
    public void shouldProvideConstraintsForChars() {
        ensureThat('a', eq('a'));
        ensureThat('a', not(eq('A')));
        ensureThat('a', eq('a'), "message");
    }
    
    public void shouldProvideConstraintsForBooleans() {

        ensureThat(true, eq(true));
        ensureThat(true, not(eq(false)));
        ensureThat(true, eq(true), "message");
    }
}

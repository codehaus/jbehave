package jbehave.core;

import jbehave.core.exception.VerificationException;
import jbehave.core.mock.UsingConstraints;

public class UsingConstraintsBehaviour extends UsingConstraints {

    Block EXCEPTION_BLOCK = new Block() {
        public void run() throws Exception {
            throw new NumberFormatException();
        }
    };
    
    
    Block EMPTY_BLOCK = new Block() {
        public void run() throws Exception {}
    };
    
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
    
    public void shouldProvideConstraintToCheckForNull() {
        ensureThat(null, isNull());
        ensureThat(new Object(), isNotNull());
    }

    public void shouldEnsureThatBlocksThrowTheRightTypeOfExceptionOrNot() throws Exception {
        
        ensureThrows(IllegalArgumentException.class, EXCEPTION_BLOCK);
        ensureDoesNotThrowException(EMPTY_BLOCK);
        
        try {
            ensureThrows(IllegalArgumentException.class, EMPTY_BLOCK);
            fail("Should have thrown a verification exception");
        } catch (VerificationException e) {
            // expected
        }
        
        try {
            ensureDoesNotThrowException(EXCEPTION_BLOCK);
            fail("Should have thrown a verification exception");
        } catch (VerificationException e) {
            // expected
        }
    }
}

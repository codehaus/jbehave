package jbehave.core;

import jbehave.core.exception.VerificationException;
import jbehave.core.mock.UsingMatchers;

public class UsingMatchersBehaviour {

    Block EXCEPTION_BLOCK = new Block() {
        public void run() throws Exception {
            throw new NumberFormatException();
        }
    };
    
    
    Block EMPTY_BLOCK = new Block() {
        public void run() throws Exception {}
    };
    
    public void shouldProvideMatchersForDoubles() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(5.0, m.eq(5.0));
        Ensure.that(5.0, m.not(m.eq(5.1)));
        Ensure.that(5.0, m.eq(5.0), "message");
    }
    
    public void shouldProvideMatchersForLongsAndInts() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(5, m.eq(5));
        Ensure.that(5, m.not(m.eq(4)));
        Ensure.that(5, m.eq(5), "message");
    }
    
    public void shouldProvideMatchersForChars() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that('a', m.eq('a'));
        Ensure.that('a', m.not(m.eq('A')));
        Ensure.that('a', m.eq('a'), "message");
    }
    
    public void shouldProvideMatchersForBooleans() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(true, m.eq(true));
        Ensure.that(true, m.not(m.eq(false)));
        Ensure.that(true, m.eq(true), "message");
    }
    
    public void shouldProvideMatcherToCheckForNull() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(null, m.isNull());
        Ensure.that(new Object(), m.isNotNull());
    }

    public void shouldFailWhenBlockThatShouldFailDoesNot() throws Exception {
        
        Ensure.throwsException(IllegalArgumentException.class, EXCEPTION_BLOCK);
        
        boolean succeeded = true;
        try {
            Ensure.throwsException(IllegalArgumentException.class, EMPTY_BLOCK);
            succeeded = false;
        } catch (VerificationException expected) {}
        
        if (!succeeded) {
            throw new VerificationException("Should have thrown a verification exception");
        }
    }
    
    public void shouldFailWhenBlockThatShouldSucceedDoesNot() throws Exception {
        Ensure.doesNotThrowException(EMPTY_BLOCK);
        
        boolean succeeded = true;
        try {
            Ensure.doesNotThrowException(EXCEPTION_BLOCK);
            succeeded = false;
        } catch (VerificationException expected) {}
        if (!succeeded) {
            throw new VerificationException("Should have thrown a verification exception");
        }
    }
}

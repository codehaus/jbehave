package org.jbehave.core;

import org.jbehave.core.mock.UsingMatchers;

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

    public void shouldCatchAndReturnAThrownException() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        Exception exception = m.runAndCatch(IllegalArgumentException.class, EXCEPTION_BLOCK);
        Ensure.that(exception, m.isNotNull());
    }
    
    public void shouldReturnNullIfNoExceptionThrown() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        Exception exception = m.runAndCatch(IllegalArgumentException.class, EMPTY_BLOCK);
        Ensure.that(exception, m.isNull());
    }
    
    public void shouldPropagateExceptionOfAnUnexpectedType() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        try {
            Exception exception = m.runAndCatch(UnsupportedOperationException.class, EXCEPTION_BLOCK);
            m.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    public void shouldProvideMatchersForOneOrAnotherCondition() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        Ensure.that(true, m.or(m.eq(true), m.eq(false)));
        Ensure.that(false, m.either(m.eq(true), m.eq(false)));
    }
}

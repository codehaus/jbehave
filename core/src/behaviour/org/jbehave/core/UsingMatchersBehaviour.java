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
    
    public void shouldProvideMatchersToCheckForNull() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(null, m.isNull());
        Ensure.that(new Object(), m.not(m.isNull()));
        Ensure.that(new Object(), m.isNotNull());
        Ensure.that(null, m.not(m.isNotNull()));
    }
    
    public void shouldProvideMatchersToCheckForAnything() {
        UsingMatchers m = new UsingMatchers() {};
        Ensure.that(null, m.not(m.nothing()));
        Ensure.that(new Object(), m.not(m.nothing()));
        Ensure.that(new Object(), m.anything());
        Ensure.that(null, m.anything());
    }
    
    public void shouldProvideCommonStringMatchers() {
        UsingMatchers m = new UsingMatchers() {};
        
        Ensure.that("octopus", m.contains("top"));
        Ensure.that("octopus", m.not(m.contains("eight")));
        Ensure.that("octopus", m.startsWith("octo"));
        Ensure.that("octopus", m.not(m.startsWith("eight")));
        Ensure.that("octopus", m.endsWith("pus"));
        Ensure.that("octopus", m.not(m.endsWith("eight")));
    }

    public void shouldProvideInstanceMatchers() {
        UsingMatchers m = new UsingMatchers() {};
        
        String a = "a";
        String b = "b";
        
        Ensure.that(a, m.is(a));
        Ensure.that(a, m.not(m.is(b)));
        Ensure.that(a, m.sameInstanceAs(a));
        Ensure.that(a, m.not(m.sameInstanceAs(b)));
    }
    
    public void shouldCatchAndReturnAThrownException() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        Exception exception = m.runAndCatch(IllegalArgumentException.class, EXCEPTION_BLOCK);
        Ensure.that(exception, m.isNotNull());
    }
    
    public void shouldCatchAndReturnNullIfNoExceptionThrown() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        Exception exception = m.runAndCatch(IllegalArgumentException.class, EMPTY_BLOCK);
        Ensure.that(exception, m.isNull());
    }
    
    public void shouldCatchAndRethrowExceptionOfAnUnexpectedType() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        try {
            Exception exception = m.runAndCatch(UnsupportedOperationException.class, EXCEPTION_BLOCK);
            m.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    public void shouldProvideConditionalMatchers() throws Exception {
        UsingMatchers m = new UsingMatchers() {};
        
        String horse = "horse";
        String cow = "cow";
        
        Ensure.that(horse, m.or(m.eq(horse), m.eq(cow)));
        Ensure.that(cow, m.either(m.eq(horse), m.eq(cow)));
        
        Ensure.that(horse, m.and(m.eq(horse), m.contains("ors")));
        Ensure.that(cow, m.both(m.eq(cow), m.endsWith("ow")));
    }
}

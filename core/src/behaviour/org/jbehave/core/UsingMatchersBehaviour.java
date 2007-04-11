package org.jbehave.core;

import java.util.ArrayList;

import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.UsingMatchers;

public class UsingMatchersBehaviour {

    private static final String NL = System.getProperty("line.separator");

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
    
    public void shouldProvideMatchersForCollectionsContainingAThing() {
        UsingMatchers m = new UsingMatchers() {};
        
        ArrayList list = new ArrayList();
        list.add(new Integer(3));
        
        Ensure.that(list, m.collectionContaining(new Integer(3)));
        Ensure.that(list, m.collectionContaining(m.eq(new Integer(3))));
        
        Ensure.that(list, m.not(m.collectionContaining(new Integer(5))));
        Ensure.that(list, m.not(m.collectionContaining(m.eq(new Integer(5)))));
    }
    
    public void shouldDescribeMatchersForCollections() {
        UsingMatchers m = new UsingMatchers() {};
        
        ArrayList list = new ArrayList();
        list.add(new Integer(5));
        
        try {
            Ensure.that(list, m.collectionContaining(new Integer(3)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), m.eq("Expected: " + NL + "a collection containing [equal to <3>]" + NL + "but got: " + NL + "[5]"));
        }
        
        list.add(new Integer(6));
        
        try {
            Ensure.that(list, m.collectionContaining(new Integer(4), new Integer(5)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), m.eq("Expected: " + NL + "a collection containing [equal to <4>, equal to <5>]" + NL + "but got: " + NL + "[5, 6]"));
        }
    }
}

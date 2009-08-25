package org.jbehave.core.matchers;

import org.jbehave.core.Block;
import org.jbehave.core.Ensure;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;

public class UsingExceptionsBehaviour {
    Block EXCEPTION_BLOCK = new Block() {
        public void run() throws Exception {
            throw new NumberFormatException();
        }
    };
        
    Block EMPTY_BLOCK = new Block() {
        public void run() throws Exception {}
    };
    
    public void shouldCatchAndReturnAThrownException() throws Exception {
        Exception exception = UsingExceptions.runAndCatch(IllegalArgumentException.class, EXCEPTION_BLOCK);
        Ensure.that(exception, UsingEqualityMatchers.isNotNull());
    }
    
    public void shouldCatchAndReturnNullIfNoExceptionThrown() throws Exception {
        Exception exception = UsingExceptions.runAndCatch(IllegalArgumentException.class, EMPTY_BLOCK);
        Ensure.that(exception, UsingEqualityMatchers.isNull());
    }
    
    public void shouldCatchAndRethrowExceptionOfAnUnexpectedType() throws Exception {
        try {
            Exception exception = UsingExceptions.runAndCatch(UnsupportedOperationException.class, EXCEPTION_BLOCK);
            throw new VerificationException("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    public void shouldThrowAVerificationExceptionOnFailure() {
        try {
            UsingExceptions.fail("Oh noooo...");
            throw new ADifferentSortOfException();
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Oh noooo..."));
        } catch (ADifferentSortOfException e) {
            throw new VerificationException("Expected a VerificationException"); // really fail
        }
        
        IllegalArgumentException cause = new IllegalArgumentException();
        try {
            UsingExceptions.fail("Oh noooo...", cause);
            throw new ADifferentSortOfException();
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Oh noooo..."));
            Ensure.that(e.getCause(), UsingEqualityMatchers.is(cause));
        } catch (ADifferentSortOfException e) {
            throw new VerificationException("Expected a VerificationException");
        }
        
        try {
            UsingExceptions.fail("Oh noooo...", "The Spanish Inquisition", "A Lumberjack!");
            throw new ADifferentSortOfException();
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Oh noooo..."));
            Ensure.that(e.getExpected(), UsingEqualityMatchers.eq("The Spanish Inquisition"));
            Ensure.that(e.getActual(), UsingEqualityMatchers.eq("A Lumberjack!"));
        } catch (ADifferentSortOfException e) {
            throw new VerificationException("Expected a VerificationException");
        }
    }
    
    public void shouldThrowAPendingExceptionOnTodo() {
        try {
            UsingExceptions.pending();
            throw new VerificationException("Expected a Pending exception");
        } catch (PendingException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("TODO"));
        }
        
        try {
            UsingExceptions.pending("Later...");
            throw new VerificationException("Expected a Pending exception");
        } catch (PendingException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Later..."));
        }
        
        try {
            UsingExceptions.todo();
            throw new VerificationException("Expected a Pending exception");
        } catch (PendingException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("TODO"));
        }
        
        try {
            UsingExceptions.todo("Later...");
            throw new VerificationException("Expected a Pending exception");
        } catch (PendingException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Later..."));
        }
    }
    
    private class ADifferentSortOfException extends RuntimeException {}
}

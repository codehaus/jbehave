/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.listeners;

import java.io.StringWriter;

import jbehave.core.exception.PendingException;
import jbehave.core.exception.VerificationException;
import jbehave.core.responsibility.Result;
import jbehave.core.responsibility.Verify;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerBehaviour {
    private static class StatefulTimer extends Timer {
        public boolean isRunning = false;
        public void start() {
            isRunning = true;
        }
        public void stop() {
            isRunning = false;
        }
    }
    
    private StringWriter writer;
    private TextListener listener;
    private StatefulTimer timer;

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = new TextListener(writer, timer);
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldSucceed", null), null);
        Verify.equal(TextListener.SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldThrowException", new Exception()), null);
        Verify.equal(TextListener.EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldFail", new VerificationException("oops")), null);
        Verify.equal(TextListener.FAILURE, writer.toString());
    }

    public void shouldRenderPendingSymbolForPending() throws Exception {
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldFail", new PendingException()), null);
        Verify.equal(TextListener.PENDING, writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n>>>\n" + output + "\n<<<", output.indexOf(expected) != -1);
    }
    
    public static class BehaviourClass {}

    public void shouldSummarizeSingleSuccessfulResponsibility() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result(BehaviourClass.class.getName(), "shouldDoX", null), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nResponsibilities: 1");
    }

    public void shouldSummarizeTwoSuccessfulResponsibilities() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", null), new Object());
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoY", null), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nResponsibilities: 2");
    }

    public void shouldSummarizeResponsibilityWithVerificationFailure() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", new VerificationException("oops")), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nResponsibilities: 1. Failures: 1");
    }

    public void shouldPrintStackTraceForCriteronWithVerificationFailure() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", new VerificationException("oops")), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldSummarizeResponsibilityWithExceptionThrown() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", new Exception()), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nResponsibilities: 1. Failures: 0, Exceptions: 1");
    }

    public void shouldPrintStackTraceForException() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", new Exception()), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("java.lang.Exception");
    }

    public void shouldSummarizePendingResponsibility() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.responsibilityVerificationEnding(new Result("SomeClass", "shouldDoX", new PendingException()), new Object());
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nPending: 1");
    }
    
    public static class OuterBehaviourClass {}
    public static class NestedBehaviourClass {}

    public void shouldNotStartTimerBeforeBehaviourClassVerificationStarts() throws Exception {
        Verify.not(timer.isRunning);
    }
    public void shouldStartTimerWhenBehaviourClassVerificationStarts() throws Exception {
        // execute
        listener.behaviourClassVerificationStarting(OuterBehaviourClass.class);
        // verify
        Verify.that(timer.isRunning);
    }
    
    public void shouldStopTimerWhenBehaviourClassVerificationEnds() throws Exception {
        // setup
        listener.behaviourClassVerificationStarting(OuterBehaviourClass.class);
        // execute
        listener.behaviourClassVerificationEnding(OuterBehaviourClass.class);
        // verify
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStartTimerAgainWhenNestedBehaviourClassVerificationStarts() throws Exception {
        // setup
        listener.behaviourClassVerificationStarting(OuterBehaviourClass.class);
        timer.isRunning = false; // reset timer
        // execute
        listener.behaviourClassVerificationStarting(NestedBehaviourClass.class);
        // verify
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStopTimerWhenNestedBehaviourClassVerificationEnds() throws Exception {
        // setup
        listener.behaviourClassVerificationStarting(OuterBehaviourClass.class);
        listener.behaviourClassVerificationStarting(NestedBehaviourClass.class);
        // execute
        listener.behaviourClassVerificationEnding(NestedBehaviourClass.class);
        // verify
        Verify.that(timer.isRunning); // hasn't been stopped
    }
    
    public void shouldStopTimerWhenOuterBehaviourClassVerificationEnds() throws Exception {
        // setup
        listener.behaviourClassVerificationStarting(OuterBehaviourClass.class);
        listener.behaviourClassVerificationStarting(NestedBehaviourClass.class);
        listener.behaviourClassVerificationEnding(NestedBehaviourClass.class);
        // execute
        listener.behaviourClassVerificationEnding(OuterBehaviourClass.class);
        // verify
        Verify.not(timer.isRunning);
    }
}

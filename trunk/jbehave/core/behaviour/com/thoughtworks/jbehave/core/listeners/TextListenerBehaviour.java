/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.io.StringWriter;

import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;

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
    private BehaviourMethod behaviourMethod;

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = new TextListener(writer, timer);
        behaviourMethod = new BehaviourMethod(null, null, new Object());
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.behaviourVerificationEnding(new Result("shouldSucceed", Result.SUCCEEDED), behaviourMethod);
        Verify.equal(TextListener.SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.behaviourVerificationEnding(new Result("shouldThrowException", Result.THREW_EXCEPTION), behaviourMethod);
        Verify.equal(TextListener.EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.behaviourVerificationEnding(new Result("shouldFail", Result.FAILED), behaviourMethod);
        Verify.equal(TextListener.FAILURE, writer.toString());
    }

    public void shouldRenderPendingSymbolForPending() throws Exception {
        listener.behaviourVerificationEnding(new Result("shouldFail", new PendingException()), behaviourMethod);
        Verify.equal(TextListener.PENDING, writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n>>>\n" + output + "\n<<<", output.indexOf(expected) != -1);
    }
    
    public static class BehaviourClass {}

    public void shouldSummarizeSingleSuccessfulMethod() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", Result.SUCCEEDED), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nMethods: 1");
    }

    public void shouldSummarizeTwoSuccessfulMethods() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", Result.SUCCEEDED), behaviourMethod);
        listener.behaviourVerificationEnding(new Result("shouldDoY", Result.SUCCEEDED), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nMethods: 2");
    }

    public void shouldPrintSummaryWhenMethodFails() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", new VerificationException("oops")), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nMethods: 1. Failures: 1");
    }

    public void shouldPrintStackTraceWhenMethodFails() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", new VerificationException("oops")), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) Object shouldDoX [java.lang.Object]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldPrintSummaryWhenMethodThrowsException() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", new Exception()), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nMethods: 1. Failures: 0, Exceptions: 1");
    }

    public void shouldPrintStackTraceWhenMethodThrowsException() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", new Exception()), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) Object shouldDoX [java.lang.Object]:");
        verifyOutputContains("java.lang.Exception");
    }
    
    public void shouldSummarizePendingMethod() throws Exception {
        listener.behaviourClassVerificationStarting(BehaviourClass.class);
        listener.behaviourVerificationEnding(new Result("shouldDoX", new PendingException()), behaviourMethod);
        listener.behaviourClassVerificationEnding(BehaviourClass.class);
        verifyOutputContains("\nPending: 1");
    }
    
    public static class OuterBehaviourClass {}
    public static class NestedBehaviourClass {}

    public void shouldEnsureTimerIsRunningWhenBehaviourClassVerificationStarts() throws Exception {
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

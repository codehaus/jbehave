/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.framework.listeners;

import java.io.StringWriter;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.Verify;
import jbehave.framework.exception.VerificationException;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerSpec {
    private static final String SUCCESS = ".";
    private static final String FAILURE = "F";
    private static final String EXCEPTION_THROWN = "E";
    
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
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldSucceed", "SomeClass", null));
        Verify.equal(SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldThrowException", "SomeClass", new Exception()));
        Verify.equal(EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldFail", "SomeClass", new VerificationException("oops")));
        Verify.equal(FAILURE, writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n>>>\n" + output + "\n<<<", output.indexOf(expected) != -1);
    }
    
    public static class Spec {}

    public void shouldSummarizeSingleSuccessfulCriteria() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", Spec.class.getName(), null));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("\nCriteria: 1");
    }

    public void shouldSummarizeTwoSuccessfulCriteria() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", "SomeClass", null));
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoY", "SomeClass", null));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("\nCriteria: 2");
    }

    public void shouldSummarizeCriterionWithVerificationFailure() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", "SomeClass", new VerificationException("oops")));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("\nCriteria: 1. Failures: 1");
    }

    public void shouldPrintStackTraceForCriteronWithVerificationFailure() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", "SomeClass", new VerificationException("oops")));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldSummarizeCriterionWithExceptionThrown() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", "SomeClass", new Exception()));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("\nCriteria: 1. Failures: 0, Exceptions: 1");
    }

    public void shouldPrintStackTraceForException() throws Exception {
        listener.specVerificationStarting(Spec.class);
        listener.criteriaVerificationEnding(new CriteriaVerification("shouldDoX", "SomeClass", new Exception()));
        listener.specVerificationEnding(Spec.class);
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("java.lang.Exception");
    }
    
    public static class OuterSpec {}
    public static class NestedSpec {}

    public void shouldNotStartTimerBeforeSpecVerificationStarts() throws Exception {
        Verify.not(timer.isRunning);
    }
    public void shouldStartTimerWhenSpecVerificationStarts() throws Exception {
        // execute
        listener.specVerificationStarting(OuterSpec.class);
        // verify
        Verify.that(timer.isRunning);
    }
    
    public void shouldStopTimerWhenSpecVerificationEnds() throws Exception {
        // setup
        listener.specVerificationStarting(OuterSpec.class);
        // execute
        listener.specVerificationEnding(OuterSpec.class);
        // verify
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStartTimerAgainWhenNestedSpecVerificationStarts() throws Exception {
        // setup
        listener.specVerificationStarting(OuterSpec.class);
        timer.isRunning = false; // reset timer
        // execute
        listener.specVerificationStarting(NestedSpec.class);
        // verify
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStopTimerWhenNestedSpecVerificationEnds() throws Exception {
        // setup
        listener.specVerificationStarting(OuterSpec.class);
        listener.specVerificationStarting(NestedSpec.class);
        // execute
        listener.specVerificationEnding(NestedSpec.class);
        // verify
        Verify.that(timer.isRunning); // hasn't been stopped
    }
    
    public void shouldStopTimerWhenOuterSpecVerificationEnds() throws Exception {
        // setup
        listener.specVerificationStarting(OuterSpec.class);
        listener.specVerificationStarting(NestedSpec.class);
        listener.specVerificationEnding(NestedSpec.class);
        // execute
        listener.specVerificationEnding(OuterSpec.class);
        // verify
        Verify.not(timer.isRunning);
    }
}

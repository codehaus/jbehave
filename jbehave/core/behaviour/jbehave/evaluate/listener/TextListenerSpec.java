/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.evaluate.listener;

import java.io.StringWriter;

import jbehave.framework.CriteriaVerificationResult;
import jbehave.framework.VerificationException;
import jbehave.framework.Verify;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerSpec {
    private static final String SUCCESS = ".";
    private static final String FAILURE = "F";
    private static final String EXCEPTION_THROWN = "E";
    
    private static class MockTimer extends Timer {

        private boolean started = false;

        public boolean isStarted()  {
            return started;
        }
        
        public void start() {
            started = true;
        }
        
        public void stop() {
            started = false;
        }
    }
    
    private StringWriter writer;
    private TextListener listener;
    private MockTimer timer;

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new MockTimer();
        listener = new TextListener(writer, timer);
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldSucceed", "SomeClass", null));
        Verify.equal(SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldThrowException", "SomeClass", null, new Exception()));
        Verify.equal(EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldFail", "SomeClass", null, new VerificationException("oops")));
        Verify.equal(FAILURE, writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n" + output, output.indexOf(expected) != -1);
    }

    public void shouldSummarizeSingleSuccessfulCriterion() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1");
    }

    public void shouldSummarizeTwoSuccessfulCriteria() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null));
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoY", "SomeClass", null));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 2");
    }

    public void shouldSummarizeCriterionWithVerificationFailure() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null, new VerificationException("oops")));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1, Failures: 1");
    }

    public void shouldPrintStackTraceForCriteronWithVerificationFailure() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null, new VerificationException("oops")));
        listener.runEnded(null);
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldSummarizeCriterionWithExceptionThrown() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null, new Exception()));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1, Failures: 0, Exceptions Thrown: 1");
    }

    public void shouldPrintStackTraceForException() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriteriaVerificationResult("shouldDoX", "SomeClass", null, new Exception()));
        listener.runEnded(null);
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("java.lang.Exception");
    }
    public void shouldStartTimerWhenEvaluationStarts() throws Exception {
        Verify.not(timer.isStarted());
        listener.runStarted(null);
        Verify.that(timer.isStarted());
    }
    
    public void shouldStopTimerWhenEvaluationEnds() throws Exception {
        timer.start();
        listener.runEnded(null);
        Verify.not(timer.isStarted());
    }
}

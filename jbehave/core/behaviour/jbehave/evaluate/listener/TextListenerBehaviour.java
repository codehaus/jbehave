/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.evaluate.listener;

import java.io.StringWriter;

import jbehave.framework.CriterionEvaluation;
import jbehave.framework.VerificationException;
import jbehave.framework.Verify;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerBehaviour {
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

    public void shouldRenderSuccessSymbolForSuccessfulBehaviour() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldSucceed", "SomeClass", null));
        Verify.equal(SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForBehaviourThatThrowsException() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldThrowException", "SomeClass", null, new Exception()));
        Verify.equal(EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailedBehaviour() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldFail", "SomeClass", null, new VerificationException("oops")));
        Verify.equal(FAILURE, writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n" + output, output.indexOf(expected) != -1);
    }

    public void shouldSummarizeSingleSuccessfulBehaviour() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1");
    }

    public void shouldSummarizeTwoSuccessfulBehaviours() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null));
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoY", "SomeClass", null));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 2");
    }

    public void shouldSummarizeBehaviourWithVerificationFailure() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null, new VerificationException("oops")));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1, Failures: 1");
    }

    public void shouldPrintStackTraceForBehaviourWithVerificationFailure() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null, new VerificationException("oops")));
        listener.runEnded(null);
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldSummarizeBehaviourWithExceptionThrown() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null, new Exception()));
        listener.runEnded(null);
        verifyOutputContains("\nBehaviours run: 1, Failures: 0, Exceptions Thrown: 1");
    }

    public void shouldPrintStackTraceForBehaviourWithExceptionThrown() throws Exception {
        listener.afterCriterionEvaluationEnds(new CriterionEvaluation("shouldDoX", "SomeClass", null, new Exception()));
        listener.runEnded(null);
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) shouldDoX [SomeClass]:");
        verifyOutputContains("java.lang.Exception");
    }
    public void shouldStartTimerWhenRunStarts() throws Exception {
        Verify.not(timer.isStarted());
        
        // execute
        listener.runStarted(null);
        
        Verify.that(timer.isStarted());
    }
    
    public void shouldStopTimerWhenRunEnds() throws Exception {
        // setup
        timer.start();
        
        // execute
        listener.runEnded(null);
        
        Verify.not(timer.isStarted());
    }
}

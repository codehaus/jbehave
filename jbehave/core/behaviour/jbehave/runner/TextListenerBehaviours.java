/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 Dan North
 *
 * See license.txt for licence details
 */
package jbehave.runner;

import java.io.StringWriter;

import jbehave.framework.BehaviourResult;
import jbehave.framework.VerificationException;
import jbehave.framework.Verify;
import jbehave.runner.listener.Listener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerBehaviours {
    private StringWriter writer;
    private Listener listener;

    private static final String SUCCESS = ".";
    private static final String ASSERTION_FAILED = "F";
    private static final String EXCEPTION_THROWN = "E";

    protected void setUp() throws Exception {
        writer = new StringWriter();
        listener = new TextListener(writer);
    }

    protected void tearDown() throws Exception {
        writer = null;
        listener = null;
    }

    public void shouldRenderSuccessSymbolForSuccessfulBehaviour() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldSucceed", "SomeClass", null));
        Verify.equal(SUCCESS, writer.toString());
    }

    public void shouldRenderExceptionSymbolForBehaviourThatThrowsException() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldThrowException", "SomeClass", new Exception()));
        Verify.equal(EXCEPTION_THROWN, writer.toString());
    }

    public void shouldRenderFailureSymbolForBehaviourThatFailsAssertion() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldFail", "SomeClass", new VerificationException("oops")));
        Verify.equal(ASSERTION_FAILED, writer.toString());
    }

    private void assertOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n" + output,
            output.indexOf(expected) != -1);
    }

    public void shouldSummarizeSingleSuccessfulBehaviour() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", null));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1");
    }

    public void shouldSummarizeTwoSuccessfulBehaviours() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", null));
        listener.behaviourEnded(new BehaviourResult("shouldDoY", "SomeClass", null));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 2");
    }

    public void shouldSummarizeBehaviourWithAssertionFailure() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new VerificationException("oops")));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1, Assertion Failures: 1");
    }

    public void shouldPrintStackTraceForBehaviourWithAssertionFailure() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new VerificationException("oops")));
        listener.runEnded(null);
        assertOutputContains("Assertion Failures:");
        assertOutputContains("\n1) shouldDoX [SomeClass]:");
        assertOutputContains("junit.framework.AssertionFailedError");
    }

    public void shouldSummarizeBehaviourWithExceptionThrown() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new Exception()));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1, Assertion Failures: 0, Exceptions Thrown: 1");
    }

    public void shouldPrintStackTraceForBehaviourWithExceptionThrown() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new Exception()));
        listener.runEnded(null);
        assertOutputContains("Exceptions Thrown:");
        assertOutputContains("\n1) shouldDoX [SomeClass]:");
        assertOutputContains("java.lang.Exception");
    }
}

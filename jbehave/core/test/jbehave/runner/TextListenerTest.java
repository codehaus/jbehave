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
import jbehave.runner.listener.Listener;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerTest extends TestCase {
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

    public void testShouldRenderSuccessSymbolForSuccessfulBehaviour() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldSucceed", "SomeClass"));
        assertEquals(SUCCESS, writer.toString());
    }
    
    public void testShouldRenderExceptionSymbolForBehaviourThatThrowsException() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldThrowException", "SomeClass", new Exception()));
        assertEquals(EXCEPTION_THROWN, writer.toString());
    }
    
    public void testShouldRenderFailureSymbolForBehaviourThatFailsAssertion() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldFail", "SomeClass", new AssertionFailedError()));
        assertEquals(ASSERTION_FAILED, writer.toString());
    }

    private void assertOutputContains(String expected) {
        String output = writer.toString();
        assertTrue("Output should contain: [" + expected + "] but was:\n" + output,
            output.indexOf(expected) != -1);
    }
    
    public void testShouldSummarizeSingleSuccessfulBehaviour() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass"));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1");
    }
    
    public void testShouldSummarizeTwoSuccessfulBehaviours() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass"));
        listener.behaviourEnded(new BehaviourResult("shouldDoY", "SomeClass"));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 2");
    }
    
    public void testShouldSummarizeBehaviourWithAssertionFailure() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new AssertionFailedError()));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1, Assertion Failures: 1");
    }
    
    public void testShouldPrintStackTraceForBehaviourWithAssertionFailure() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new AssertionFailedError()));
        listener.runEnded(null);
        assertOutputContains("Assertion Failures:");
        assertOutputContains("\n1) shouldDoX [SomeClass]:");
        assertOutputContains("junit.framework.AssertionFailedError");
    }
    
    public void testShouldSummarizeBehaviourWithExceptionThrown() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new Exception()));
        listener.runEnded(null);
        assertOutputContains("\nBehaviours run: 1, Assertion Failures: 0, Exceptions Thrown: 1");
    }

    public void testShouldPrintStackTraceForBehaviourWithExceptionThrown() throws Exception {
        listener.behaviourEnded(new BehaviourResult("shouldDoX", "SomeClass", new Exception()));
        listener.runEnded(null);
        assertOutputContains("Exceptions Thrown:");
        assertOutputContains("\n1) shouldDoX [SomeClass]:");
        assertOutputContains("java.lang.Exception");
    }
}

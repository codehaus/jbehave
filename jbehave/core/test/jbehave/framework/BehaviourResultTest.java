/*
 * Created on 07-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.framework;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourResultTest extends TestCase {
    
    private void assertState(BehaviourResult result, int status, boolean succeeded, boolean assertionFailed, boolean exceptionThrown) {
        assertEquals(status, result.getStatus());
        assertEquals(succeeded, result.succeeded());
        assertEquals(assertionFailed, result.assertionFailed());
        assertEquals(exceptionThrown, result.exceptionThrown());
    }
    
    public void testShouldHaveConsistentStateForSuccessfulBehaviour() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldSucceed", "SomeClass");
        assertState(result, BehaviourResult.SUCCESS, true, false, false);
    }

    public void testShouldHaveConsistentStateForAssertionFailure() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldFail", "SomeClass", new AssertionFailedError());
        assertState(result, BehaviourResult.ASSERTION_FAILED, false, true, false);
    }

    public void testShouldHaveConsistentStateForExceptionThrown() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldThrowException", "SomeClass", new Exception());
        assertState(result, BehaviourResult.EXCEPTION_THROWN, false, false, true);
    }
}

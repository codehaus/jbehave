/*
 * Created on 07-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.framework;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourResultBehaviours {

    private void verifyState(BehaviourResult result, int status, boolean succeeded, boolean failed, boolean exceptionThrown) {
        Verify.equal("status", status, result.getStatus());
        Verify.equal("succeeded", succeeded, result.succeeded());
        Verify.equal("failed", failed, result.failed());
        Verify.equal("exception thrown", exceptionThrown, result.exceptionThrown());
    }

    public void shouldHaveConsistentStateForSuccessfulBehaviour() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldSucceed", "SomeClass", null);
        verifyState(result, BehaviourResult.SUCCESS, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldFail", "SomeClass", null, new VerificationException("oops"));
        verifyState(result, BehaviourResult.FAILURE, false, true, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        BehaviourResult result = new BehaviourResult("shouldThrowException", "SomeClass", null, new Exception());
        verifyState(result, BehaviourResult.EXCEPTION_THROWN, false, false, true);
    }
}

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

    private void verifyState(Evaluation result, int status, boolean succeeded, boolean failed, boolean exceptionThrown) {
        Verify.equal("status", status, result.getStatus());
        Verify.equal("succeeded", succeeded, result.succeeded());
        Verify.equal("failed", failed, result.failed());
        Verify.equal("exception thrown", exceptionThrown, result.exceptionThrown());
    }

    public void shouldHaveConsistentStateForSuccessfulBehaviour() throws Exception {
        Evaluation result = new Evaluation("shouldSucceed", "SomeClass", null);
        verifyState(result, Evaluation.SUCCESS, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Evaluation result = new Evaluation("shouldFail", "SomeClass", null, new VerificationException("oops"));
        verifyState(result, Evaluation.FAILURE, false, true, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        Evaluation result = new Evaluation("shouldThrowException", "SomeClass", null, new Exception());
        verifyState(result, Evaluation.EXCEPTION_THROWN, false, false, true);
    }
}

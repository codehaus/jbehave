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
public class CriteriaEvaluationBehaviour {

    private void verifyEvaluationState(Result evaluation, int status, boolean succeeded, boolean failed, boolean exceptionThrown) {
        Verify.equal("status", status, evaluation.getStatus());
        Verify.equal("succeeded", succeeded, evaluation.succeeded());
        Verify.equal("failed", failed, evaluation.failed());
        Verify.equal("exception thrown", exceptionThrown, evaluation.exceptionThrown());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        Result result = new Result("shouldSucceed", "SomeClass", null);
        verifyEvaluationState(result, Result.SUCCESS, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Result result = new Result("shouldFail", "SomeClass", null, new VerificationException("oops"));
        verifyEvaluationState(result, Result.FAILURE, false, true, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        Result result = new Result("shouldThrowException", "SomeClass", null, new Exception());
        verifyEvaluationState(result, Result.EXCEPTION_THROWN, false, false, true);
    }
}

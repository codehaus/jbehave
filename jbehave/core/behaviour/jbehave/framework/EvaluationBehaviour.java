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
public class EvaluationSpec {

    private void verifyEvaluationState(Evaluation evaluation, int status, boolean succeeded, boolean failed, boolean exceptionThrown) {
        Verify.equal("status", status, evaluation.getStatus());
        Verify.equal("succeeded", succeeded, evaluation.succeeded());
        Verify.equal("failed", failed, evaluation.failed());
        Verify.equal("exception thrown", exceptionThrown, evaluation.exceptionThrown());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        Evaluation result = new Evaluation("shouldSucceed", "SomeClass", null);
        verifyEvaluationState(result, Evaluation.SUCCESS, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Evaluation result = new Evaluation("shouldFail", "SomeClass", null, new VerificationException("oops"));
        verifyEvaluationState(result, Evaluation.FAILURE, false, true, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        Evaluation result = new Evaluation("shouldThrowException", "SomeClass", null, new Exception());
        verifyEvaluationState(result, Evaluation.EXCEPTION_THROWN, false, false, true);
    }
}

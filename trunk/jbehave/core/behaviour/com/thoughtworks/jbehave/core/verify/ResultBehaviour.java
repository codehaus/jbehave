/*
 * Created on 07-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.verify;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResultBehaviour {

    private void verifyEvaluationState(Result result, Result.ResultType status, boolean succeeded, boolean failed, boolean exceptionThrown, boolean pending) {
        Verify.equal("status", status, result.getStatus());
        Verify.equal("succeeded", succeeded, result.succeeded());
        Verify.equal("failed", failed, result.failed());
        Verify.equal("exception thrown", exceptionThrown, result.threwException());
        Verify.equal("pending", pending, result.isPending());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        Result result =
            new Result("shouldSucceed", Result.SUCCEEDED);
        verifyEvaluationState(result, Result.SUCCEEDED, true, false, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Result result =
            new Result("shouldFail", new VerificationException("oops"));
        verifyEvaluationState(result, Result.FAILED, false, true, false, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        Result result =
            new Result("shouldThrowException", new Exception());
        verifyEvaluationState(result, Result.THREW_EXCEPTION, false, false, true, false);
    }
    
    public void shouldHaveConsistentStateForPending() throws Exception {
        Result result =
            new Result("shouldBeImplemented", new PendingException("todo"));
        verifyEvaluationState(result, Result.PENDING, false, false, false, true);
    }
}

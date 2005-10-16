/*
 * Created on 07-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.result;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.minimock.UsingConstraints;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResultBehaviour extends UsingConstraints {
	
	private void verifyResult(final Result result, final Result.Type status, boolean succeeded, boolean failed, boolean exceptionThrown, boolean pending) {
        ensure(result.status(), eq(status), "status");
        ensure(result.succeeded(), eq(result.succeeded()), "succeeded");
        ensure(result.failed(), eq(failed), "failed");
        ensure(result.threwException(), eq(exceptionThrown), "exception thrown");
        ensure(result.isPending(), eq(pending),"pending");
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        Result result = new Result("shouldSucceed", "Container", Result.SUCCEEDED);
        verifyResult(result, Result.SUCCEEDED, true, false, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Result result = new Result("shouldFail", "Container", new VerificationException("oops"));
        verifyResult(result, Result.FAILED, false, true, false, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        Result result = new Result("shouldThrowException", "Container", new Exception());
        verifyResult(result, Result.THREW_EXCEPTION, false, false, true, false);
    }
    
    public void shouldHaveConsistentStateForPending() throws Exception {
        Result result = new Result("shouldBeImplemented", "Container", new PendingException("todo"));
        verifyResult(result, Result.PENDING, false, false, false, true);
    }
}

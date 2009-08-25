/*
 * Created on 07-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package org.jbehave.core.result;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.UsingMatchers;



/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResultBehaviour extends UsingMatchers {
	
	private void verifyResult(final Result result, final Result.Type status, boolean succeeded, boolean failed, boolean pending) {
        ensureThat(result.status(), eq(status), "status");
        ensureThat(result.succeeded(), eq(succeeded), "succeeded");
        ensureThat(result.failed(), eq(failed), "failed");
        ensureThat(result.isPending(), eq(pending),"pending");
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        Result result = new Result("shouldSucceed", "Container", Result.SUCCEEDED);
        verifyResult(result, Result.SUCCEEDED, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        Result result = new Result("shouldFail", "Container", new VerificationException("oops"));
        verifyResult(result, Result.FAILED, false, true, false);
    }

    public void shouldHaveConsistentStateForPending() throws Exception {
        Result result = new Result("shouldBeImplemented", "Container", new PendingException("todo"));
        verifyResult(result, Result.PENDING, false, false, true);
    }
}

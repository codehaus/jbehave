/*
 * Created on 07-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.framework;

import jbehave.framework.exception.PendingException;
import jbehave.framework.exception.VerificationException;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResponsibilityVerificationBehaviour {

    private void verifyEvaluationState(ResponsibilityVerification verification, int status, boolean succeeded, boolean failed, boolean exceptionThrown, boolean pending) {
        Verify.equal("status", status, verification.getStatus());
        Verify.equal("succeeded", succeeded, verification.succeeded());
        Verify.equal("failed", failed, verification.failed());
        Verify.equal("exception thrown", exceptionThrown, verification.threwException());
        Verify.equal("pending", pending, verification.pending());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        ResponsibilityVerification verification =
            new ResponsibilityVerification("SomeClass", "shouldSucceed", null);
        verifyEvaluationState(verification, ResponsibilityVerification.SUCCESS, true, false, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        ResponsibilityVerification verification =
            new ResponsibilityVerification("SomeClass", "shouldFail", new VerificationException("oops"));
        verifyEvaluationState(verification, ResponsibilityVerification.FAILURE, false, true, false, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        ResponsibilityVerification verification =
            new ResponsibilityVerification("SomeClass", "shouldThrowException", new Exception());
        verifyEvaluationState(verification, ResponsibilityVerification.EXCEPTION_THROWN, false, false, true, false);
    }
    
    public void shouldHaveConsistentStateForPending() throws Exception {
        ResponsibilityVerification verification =
            new ResponsibilityVerification("SomeClass", "shouldBeImplemented", new PendingException("todo"));
        verifyEvaluationState(verification, ResponsibilityVerification.PENDING, false, false, false, true);
    }
}

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
public class CriteriaVerificationSpec {

    private void verifyEvaluationState(CriteriaVerification verification, int status, boolean succeeded, boolean failed, boolean exceptionThrown, boolean pending) {
        Verify.equal("status", status, verification.getStatus());
        Verify.equal("succeeded", succeeded, verification.succeeded());
        Verify.equal("failed", failed, verification.failed());
        Verify.equal("exception thrown", exceptionThrown, verification.threwException());
        Verify.equal("pending", pending, verification.pending());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        CriteriaVerification verification =
            new CriteriaVerification("shouldSucceed", "SomeClass", null);
        verifyEvaluationState(verification, CriteriaVerification.SUCCESS, true, false, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        CriteriaVerification verification =
            new CriteriaVerification("shouldFail", "SomeClass", new VerificationException("oops"));
        verifyEvaluationState(verification, CriteriaVerification.FAILURE, false, true, false, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        CriteriaVerification verification =
            new CriteriaVerification("shouldThrowException", "SomeClass", new Exception());
        verifyEvaluationState(verification, CriteriaVerification.EXCEPTION_THROWN, false, false, true, false);
    }
    
    public void shouldHaveConsistentStateForPending() throws Exception {
        CriteriaVerification verification =
            new CriteriaVerification("shouldBeImplemented", "SomeClass", new PendingException("todo"));
        verifyEvaluationState(verification, CriteriaVerification.PENDING, false, false, false, true);
    }
}

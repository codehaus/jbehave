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
public class CriteriaVerificationSpec {

    private void verifyEvaluationState(CriteriaVerification verification, int status, boolean succeeded, boolean failed, boolean exceptionThrown) {
        Verify.equal("status", status, verification.getStatus());
        Verify.equal("succeeded", succeeded, verification.succeeded());
        Verify.equal("failed", failed, verification.failed());
        Verify.equal("exception thrown", exceptionThrown, verification.threwException());
    }

    public void shouldHaveConsistentStateForSuccess() throws Exception {
        CriteriaVerification verification = new CriteriaVerification("shouldSucceed", "SomeClass", null);
        verifyEvaluationState(verification, CriteriaVerification.SUCCESS, true, false, false);
    }

    public void shouldHaveConsistentStateForFailure() throws Exception {
        CriteriaVerification verification = new CriteriaVerification("shouldFail", "SomeClass", null, new VerificationException("oops"));
        verifyEvaluationState(verification, CriteriaVerification.FAILURE, false, true, false);
    }

    public void shouldHaveConsistentStateForExceptionThrown() throws Exception {
        CriteriaVerification verification = new CriteriaVerification("shouldThrowException", "SomeClass", null, new Exception());
        verifyEvaluationState(verification, CriteriaVerification.EXCEPTION_THROWN, false, false, true);
    }
}

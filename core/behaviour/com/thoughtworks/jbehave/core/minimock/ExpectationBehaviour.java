package com.thoughtworks.jbehave.core.minimock;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.VerificationException;

/**
 * @author <a href="mailto:nsnoek@thoughtworks.com">Nic Snoek </a>
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy </a>
 *  
 */
public class ExpectationBehaviour {

    public void shouldThrowVerificationExceptionOnInvokeIfExpectingNever() throws Throwable {
        Expectation expectation = new Expectation(null, "test").never();
        try {
            expectation.invoke(null, null, null);
            Verify.impossible("Invoke should have throw VerificationException");
        }
        catch (VerificationException ex) {
            // ok;
        }
    }

    public void shouldVerifyOKIfExpectingNeverWithoutInvoking() throws Throwable {
        Expectation expectation = new Expectation(null, "test").never();
        expectation.verify();
    }

    public void shouldMatchSimpleExpectationCorrectly() {
        Expectation expectation = new Expectation(null, "method");
        Verify.that(expectation.matches("method", null));
    }

    public void shouldMatchExpectationWithArguments() {
        Expectation expectation = new Expectation(null, "method").with("hello");
        Verify.that(expectation.matches("method", new Object[] { "hello" }));
    }

    public void shouldMatchAnyArgsWhenNoneSpecified() throws Exception {
        Expectation expectation = new Expectation(null, "method");
        Verify.that(expectation.matches("method", new Object[] { "blah" }));
    }

    public void shouldNotMatchWhenCallingWithArgsWhenNoArgsSet() throws Exception {
        Expectation expectation = new Expectation(null, "method").withNoArguments();
        Verify.not(expectation.matches("method", new Object[] { "blah" }));
    }

    public void shouldThrowVerificationExceptionOnSecondInvokeIfExpectingOnce() throws Throwable {
        Expectation expectation = new Expectation(null, "test").once();
        expectation.invoke(null, null, null);
        try {
            expectation.invoke(null, null, null);
            Verify.impossible("Invoke should have throw VerificationException");
        }
        catch (VerificationException expected) {
        }
    }

    public void shouldFailVerifyIfExpectingOnceWithoutInvoking() {
        Expectation expectation = new Expectation(null, "test").once();
        try {
            expectation.verify();
            Verify.impossible("Verify should have throw VerificationException");
        } catch (VerificationException expected) {}

    }

    public void shouldVerifyOKIfExpectingOnceAfterInvokingOnce() throws Throwable {
        Expectation expectation = new Expectation(null, "test").once();
        expectation.invoke(null, null, null);
        expectation.verify();
    }

    public void shouldVerifyOkWhenNumberOfInvocationsMatchesExpectedNumberOfTimes() throws Throwable {
        Expectation expecation = new Expectation(null, "method").times(3);
        expecation.invoke(null, null, null);
        expecation.invoke(null, null, null);
        expecation.invoke(null, null, null);

        expecation.verify();
    }

    public void shouldFailWhenNumberOfInvocationsGreaterThanExpectedNumberOfTimes() throws Throwable {
        Expectation expecation = new Expectation(null, "method").times(2);
        expecation.invoke(null, null, null);
        expecation.invoke(null, null, null);
        try {
            expecation.invoke(null, null, null);
            Verify.impossible("Should have failed verification");
        } catch (VerificationException expected) {}
    }

    public void shouldFailWhenNumberOfInvocationsLessThanExpectedNumberOfTimes()
            throws Throwable {
        Expectation expecation = new Expectation(null, "method").times(2);
        expecation.invoke(null, null, null);
        try {
            expecation.verify();
            Verify.impossible("Should have failed verification");
        } catch (VerificationException expected) {}
    }
}

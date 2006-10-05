package jbehave.core.mock;

import jbehave.core.Ensure;
import jbehave.core.exception.VerificationException;
import jbehave.core.mock.Expectation;


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
            Ensure.impossible("Invoke should have throw VerificationException");
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
        Ensure.that(expectation.matches("method", null));
    }

    public void shouldMatchExpectationWithArguments() {
        Expectation expectation = new Expectation(null, "method").with("hello");
        Ensure.that(expectation.matches("method", new Object[] { "hello" }));
    }

    public void shouldMatchAnyArgsWhenNoneSpecified() throws Exception {
        Expectation expectation = new Expectation(null, "method");
        Ensure.that(expectation.matches("method", new Object[] { "blah" }));
    }

    public void shouldNotMatchWhenCallingWithArgsWhenNoArgsSet() throws Exception {
        Expectation expectation = new Expectation(null, "method").withNoArguments();
        Ensure.not(expectation.matches("method", new Object[] { "blah" }));
    }

    public void shouldThrowExceptionWhenInvocationsEqualMaxInvocations() throws Throwable {
        Expectation expectation = new Expectation(null, "test").once();
        expectation.invoke(null, null, null);
        try {
			expectation.matches("test", null);
		} catch (Exception expected) {
		}
    }
    
    public void shouldThrowVerificationExceptionOnSecondInvokeIfExpectingOnce() throws Throwable {
        Expectation expectation = new Expectation(null, "test").once();
        expectation.invoke(null, null, null);
        try {
            expectation.invoke(null, null, null);
            Ensure.impossible("Invoke should have throw VerificationException");
        }
        catch (VerificationException expected) {
        }
    }

    public void shouldFailVerifyIfExpectingOnceWithoutInvoking() {
        Expectation expectation = new Expectation(null, "test").once();
        try {
            expectation.verify();
            Ensure.impossible("Verify should have throw VerificationException");
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
            Ensure.impossible("Should have failed verification");
        } catch (VerificationException expected) {}
    }

    public void shouldFailWhenNumberOfInvocationsLessThanExpectedNumberOfTimes()
            throws Throwable {
        Expectation expecation = new Expectation(null, "method").times(2);
        expecation.invoke(null, null, null);
        try {
            expecation.verify();
            Ensure.impossible("Should have failed verification");
        } catch (VerificationException expected) {}
    }
    
    
}

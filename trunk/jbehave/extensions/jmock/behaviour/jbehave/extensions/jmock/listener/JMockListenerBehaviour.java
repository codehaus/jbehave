/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.lang.reflect.Method;
import java.util.List;

import jbehave.extensions.jmock.UsingJMock;
import jbehave.framework.Listener;
import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ExecutingResponsibilityVerifier;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.Verify;
import junit.framework.AssertionFailedError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListenerBehaviour {
	private Listener listener;
    private ResponsibilityVerifier verifier;

	public void setUp() {
		listener = new JMockListener();
        verifier = new ExecutingResponsibilityVerifier();
	}

    public static class BehaviourClass1 implements UsingJMock {
        public boolean verifyWasCalled = false;
        
        private Mock someMock = new Mock(List.class) {
            public void verify() {
                verifyWasCalled = true;
            }
        };
        
        public void shouldDoSomething() {
            someMock.stubs();
        }
    }

    /** pull out the first responsibility method in a spec */
    private Method firstResponsibilityMethod(Class behaviourClass) throws Exception {
        Method[] methods = behaviourClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No responsibility method found in " + behaviourClass.getName());
    }

    public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        ResponsibilityVerification verification =
            verifier.verifyResponsibility(Listener.NULL, firstResponsibilityMethod(BehaviourClass1.class));
		BehaviourClass1 behaviourClassInstance = new BehaviourClass1();

        // execute
        listener.responsibilityVerificationEnding(verification, behaviourClassInstance);
        
        // verify
        Verify.that(behaviourClassInstance.verifyWasCalled);
	}

	public static class BehaviourClass2 implements UsingJMock {
        public boolean verifyWasCalled = false;

        private Mock someMock = new Mock(List.class) {
            public void verify() {
                throw new AssertionFailedError("blah was not invoked");
            }
        };

        public void shouldDoSomething() {
            someMock.stubs();
        }
    }

	public void shouldCreateNewVerificationWhenVerifyFails() throws Exception {
		// setup
        ResponsibilityVerification verification
            = verifier.verifyResponsibility(Listener.NULL,
                    firstResponsibilityMethod(BehaviourClass2.class));
        BehaviourClass2 instance = new BehaviourClass2();

        // execute
        ResponsibilityVerification verifyMockResult = listener.responsibilityVerificationEnding(verification, instance);

		// verify
		Verify.notNull(verifyMockResult);
		Verify.not(verifyMockResult == verification);
	}

	interface Foo {
		String someMethod();
	}

	public static class BehaviourClass3 implements UsingJMock {

		public void shouldUseAMockWhoseExpectationWillFail() throws Exception {
	        Mock foo = new Mock(Foo.class);
			foo.expects(Invoked.once()).method("someMethod").withNoArguments()
                .will(Return.value("hello"));
		}
	}

	public void shouldAutomaticallyVerifyMocks() throws Exception {
        // setup
        final Method method = firstResponsibilityMethod(BehaviourClass3.class);
        listener.responsibilityVerificationStarting(method);
        final BehaviourClass3 instance = new BehaviourClass3();
        instance.shouldUseAMockWhoseExpectationWillFail();
        
		// execute
		ResponsibilityVerification verification =
            listener.responsibilityVerificationEnding(
                    new ResponsibilityVerification(BehaviourClass3.class.getName(), "shouldUseAMockWhoseExpectationWillFail"),
                    instance);
        
		// verify
		Verify.that("should fail JMock verification", verification.failed());
	}
}

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

import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.Verify;
import jbehave.framework.Listener;
import jbehave.extensions.jmock.Mocker;

import org.jmock.Mock;
import org.jmock.core.matcher.InvokeOnceMatcher;
import junit.framework.AssertionFailedError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListenerSpec {
	private Listener listener;


	public void setUp() {
		listener = new JMockListener();
	}

    public static class BehaviourClassWithPrivateMock {
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

    /** pull out the first criteria method in a spec */
    private Method firstCriteria(Class spec) throws Exception {
        Method[] methods = spec.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No spec method found in " + spec.getName());
    }

    private ResponsibilityVerifier getSingleBehaviour(Class behaviourClass) throws Exception {
        return new ResponsibilityVerifier(firstCriteria(behaviourClass));
    }
    
	public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        ResponsibilityVerifier behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        ResponsibilityVerification behaviourResult = behaviour.verifyResponsibility(Listener.NULL);
		BehaviourClassWithPrivateMock spec = new BehaviourClassWithPrivateMock();

        // execute
        listener.responsibilityVerificationEnding(behaviourResult, spec);
        
        // verify
        Verify.that(spec.verifyWasCalled);
	}

	public static class BehaviourClassWithFailingMock {
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
        ResponsibilityVerifier behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        ResponsibilityVerification behaviourResult = behaviour.verifyResponsibility(Listener.NULL);
        Object spec = new BehaviourClassWithFailingMock();
		// execute
        ResponsibilityVerification verifyMockResult = listener.responsibilityVerificationEnding(behaviourResult, spec);

		// verify
		Verify.notNull(verifyMockResult);
		Verify.not(verifyMockResult == behaviourResult);
	}

	public static class BehaviourClassNeedingAMock {
		private Mocker mocker = null;

		public void needsMocks(Mocker mocker) {
			this.mocker = mocker;
		}

		public Mocker getMocker() {
			return mocker;
		}

		public void shouldNeedAMock() throws Exception {
			//
		}
	}

	public void shouldInjectMockerWhenNeedsMockMethodIsPresentOnASpec() throws Exception {
		// setup
		ResponsibilityVerifier behaviour = getSingleBehaviour(BehaviourClassNeedingAMock.class);
		BehaviourClassNeedingAMock spec = new BehaviourClassNeedingAMock();

		// execute
		listener.responsibilityVerificationStarting(behaviour, spec);

		// verify
		Verify.that("needsMocker should have been invoked", spec.getMocker() != null);
	}


	interface AnIntf {
		void someMethod();
	}

	public static class AJMockUsingSpec {
		Mocker mocker;

		public void needsMocks(Mocker mocker) {
			this.mocker = mocker;
		}

		public void shouldUseAMock() throws Exception {
	        Mock m = mocker.mock(AnIntf.class);
			m.expects(new InvokeOnceMatcher()).method("someMethod");
		}
	}

	public void shouldAutomaticallyVerifyMocksCreatedWithMocker() throws Exception {
		// setup
		ResponsibilityVerifier behaviour = getSingleBehaviour(AJMockUsingSpec.class);
		AJMockUsingSpec spec = new AJMockUsingSpec();

		// execute
	    listener.responsibilityVerificationStarting(behaviour, spec);
		spec.shouldUseAMock();
		ResponsibilityVerification verification = listener.responsibilityVerificationEnding(new ResponsibilityVerification("shouldUseAMock", "AJMockUsingSpec"), spec);
		// verify
		Verify.that("should of failed JMock verification", verification.failed());
	}
}

/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import jbehave.framework.Listener;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.Verify;

import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class CompositeListenerBehaviour {
	private CompositeListener composite;
	private Map callMap1;
	private Map callMap2;
	private ResponsibilityVerification nextVerification;

	// Mocks would be great for this :(
	public class StubListener implements Listener {
		private Map methodsCalled;
		private ResponsibilityVerification decoratedVerification;

		public StubListener(Map methodsCalled, ResponsibilityVerification decoratedVerification) {
			this.methodsCalled = methodsCalled;
			this.decoratedVerification = decoratedVerification;
		}

		public void behaviourClassVerificationStarting(Class behaviourClass) {
			methodsCalled.put("behaviourClassVerificationStarting", behaviourClass);
		}

		public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClassInstance) {
			methodsCalled.put("responsibilityVerificationStarting", verifier);
		}

		public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification verification, Object behaviourClassInstance) {
			methodsCalled.put("responsibilityVerificationEnding", verification);
			return decoratedVerification;
		}

		public void behaviourClassVerificationEnding(Class behaviourClass) {
			methodsCalled.put("behaviourClassVerificationEnding", behaviourClass);
		}
	}

	public void setUp() {
		composite = new CompositeListener();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		nextVerification = new ResponsibilityVerification("blah", "blah");
		composite.add(new StubListener(callMap1, nextVerification));
		composite.add(new StubListener(callMap2, nextVerification));
	}

	private void verifyMethodCalled(String methodName, Map callMap, Object arg) {
		Verify.equal(1, callMap.size());
		Verify.that("should of called:" + methodName, callMap.containsKey(methodName));
		Verify.equal(arg, callMap.get(methodName));
	}

	public void shouldNotifyAllListenersWhenBehaviourClassVerificationStarting() {
		// execute
		composite.behaviourClassVerificationStarting(getClass());

		// verify
		verifyMethodCalled("behaviourClassVerificationStarting", callMap1, getClass());
		verifyMethodCalled("behaviourClassVerificationStarting", callMap2, getClass());
	}

	public void shouldNotifyAllListenersWhenBehaviourClassVerificationFinished() {
		// execute
        composite.behaviourClassVerificationEnding(getClass());

		// verify
		verifyMethodCalled("behaviourClassVerificationEnding", callMap1, getClass());
		verifyMethodCalled("behaviourClassVerificationEnding", callMap2, getClass());
	}

	public void shouldNotifyAllListenersWhenResponsibilityVerificationStarting() {
		// setup
		ResponsibilityVerifier verifier = new ResponsibilityVerifier(getClass().getMethods()[0]);

		// execute
		composite.responsibilityVerificationStarting(verifier, new Object());

		// verify
		verifyMethodCalled("responsibilityVerificationStarting", callMap1, verifier);
		verifyMethodCalled("responsibilityVerificationStarting", callMap2, verifier);

	}

	public void shouldNotifyAllListenersWhenResponsibilityVerificationEndingAndPassOnReturnedResponsibilityVerifierToNextListener() {
		// setup
	    ResponsibilityVerification verification = new ResponsibilityVerification("responsibility", "behaviourClass");

		// execute
	    ResponsibilityVerification returnedVerification = composite.responsibilityVerificationEnding(verification, new Object());

		// verify
		Verify.equal(nextVerification, returnedVerification);
		verifyMethodCalled("responsibilityVerificationEnding", callMap1, verification);
		verifyMethodCalled("responsibilityVerificationEnding", callMap2, nextVerification);
	}


}

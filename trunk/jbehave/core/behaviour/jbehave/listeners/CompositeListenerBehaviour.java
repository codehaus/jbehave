/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import jbehave.framework.Listener;
import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.Verify;

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

		public void responsibilityVerificationStarting(Method responsibilityMethod) {
			methodsCalled.put("responsibilityVerificationStarting", responsibilityMethod);
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
		Verify.that("should have called:" + methodName, callMap.containsKey(methodName));
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

	public static class SomeBehaviourClass {
	    public void shouldDoSomething() throws Exception {
        }
	}
	
	public void shouldNotifyAllListenersWhenResponsibilityVerificationStarts() throws Exception {
		// setup
		// execute
		Method method = SomeBehaviourClass.class.getMethod("shouldDoSomething", new Class[0]);
        composite.responsibilityVerificationStarting(method);

		// verify
		verifyMethodCalled("responsibilityVerificationStarting", callMap1, method);
		verifyMethodCalled("responsibilityVerificationStarting", callMap2, method);

	}

	public void shouldNotifyAllListenersWhenResponsibilityVerificationEndingAndPassOnReturnedResponsibilityVerifierToNextListener() {
		// setup
	    ResponsibilityVerification verification = new ResponsibilityVerification("behaviourClass", "responsibility");

		// execute
	    ResponsibilityVerification returnedVerification = composite.responsibilityVerificationEnding(verification, new Object());

		// verify
		Verify.equal(nextVerification, returnedVerification);
		verifyMethodCalled("responsibilityVerificationEnding", callMap1, verification);
		verifyMethodCalled("responsibilityVerificationEnding", callMap2, nextVerification);
	}


}

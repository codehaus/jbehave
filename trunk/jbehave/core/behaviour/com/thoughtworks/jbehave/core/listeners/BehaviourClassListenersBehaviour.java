/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.jbehave.core.BehaviourClassListener;
import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class BehaviourClassListenersBehaviour {
	private BehaviourClassListeners listeners;
	private Map callMap1;
	private Map callMap2;

	// Mocks would be great for this :(
	public class StubListener implements BehaviourClassListener {
		private Map methodsCalled;

		public StubListener(Map methodsCalled) {
			this.methodsCalled = methodsCalled;
		}

		public void behaviourClassVerificationStarting(Class behaviourClass) {
			methodsCalled.put("behaviourClassVerificationStarting", behaviourClass);
		}

		public void behaviourClassVerificationEnding(Class behaviourClass) {
			methodsCalled.put("behaviourClassVerificationEnding", behaviourClass);
		}
	}

	public void setUp() {
		listeners = new BehaviourClassListeners();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		listeners.add(new StubListener(callMap1));
		listeners.add(new StubListener(callMap2));
	}

	private void verifyMethodCalled(String methodName, Map callMap, Object arg) {
		Verify.equal(1, callMap.size());
		Verify.that("should have called:" + methodName, callMap.containsKey(methodName));
		Verify.equal(arg, callMap.get(methodName));
	}

	public void shouldNotifyAllListenersWhenBehaviourClassVerificationStarting() {
		// execute
		listeners.behaviourClassVerificationStarting(getClass());

		// verify
		verifyMethodCalled("behaviourClassVerificationStarting", callMap1, getClass());
		verifyMethodCalled("behaviourClassVerificationStarting", callMap2, getClass());
	}

	public void shouldNotifyAllListenersWhenBehaviourClassVerificationFinished() {
		// execute
        listeners.behaviourClassVerificationEnding(getClass());

		// verify
		verifyMethodCalled("behaviourClassVerificationEnding", callMap1, getClass());
		verifyMethodCalled("behaviourClassVerificationEnding", callMap2, getClass());
	}

	public static class SomeBehaviourClass {
	    public void shouldDoSomething() throws Exception {
        }
	}
}

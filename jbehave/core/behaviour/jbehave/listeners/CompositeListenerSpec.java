/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import jbehave.framework.Listener;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;
import jbehave.framework.Verify;

import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class CompositeListenerSpec {
	private CompositeListener composite;
	private Map callMap1;
	private Map callMap2;

	// Mocks would be great for this :(
	public class StubListener implements Listener {
		private Map methodsCalled;

		public StubListener(Map methodsCalled) {
			this.methodsCalled = methodsCalled;
		}

		public void specVerificationStarting(Class spec) {
			methodsCalled.put("specVerificationStarting", spec);
		}

		public void criteriaVerificationStarting(CriteriaVerifier verifier) {
			methodsCalled.put("criteriaVerificationStarting", verifier);
		}

		public void criteriaVerificationEnding(CriteriaVerification verification) {
			methodsCalled.put("criteriaVerificationEnding", verification);
		}

		public void specVerificationEnding(Class spec) {
			methodsCalled.put("specVerificationEnding", spec);
		}
	}

	public void setUp() {
		composite = new CompositeListener();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		composite.add(new StubListener(callMap1));
		composite.add(new StubListener(callMap2));
	}

	private void verifyMethodCalled(String methodName, Map callMap, Object arg) {
		Verify.equal(1, callMap.size());
		Verify.that("should of called:" + methodName, callMap.containsKey(methodName));
		Verify.equal(arg, callMap.get(methodName));
	}

	public void shouldNotifyAllListenerWhenSpecVerificationStarting() {
		// execute
		composite.specVerificationStarting(getClass());

		// verify
		verifyMethodCalled("specVerificationStarting", callMap1, getClass());
		verifyMethodCalled("specVerificationStarting", callMap2, getClass());
	}

	public void shouldNotifyAllListenersWhenSpecVerificationFinished() {
		// execute
        composite.specVerificationEnding(getClass());

		// verify
		verifyMethodCalled("specVerificationEnding", callMap1, getClass());
		verifyMethodCalled("specVerificationEnding", callMap2, getClass());
	}

	public void shouldNotifyAllListenersWhenCriteriaVerificationStarting() {
		// setup
		CriteriaVerifier verifier = new CriteriaVerifier(getClass().getMethods()[0]);

		// execute
		composite.criteriaVerificationStarting(verifier);

		// verify
		verifyMethodCalled("criteriaVerificationStarting", callMap1, verifier);
		verifyMethodCalled("criteriaVerificationStarting", callMap2, verifier);

	}

	public void shouldNotifyAllListenersWhenCriteriaVerificationEnding() {
		// setup
	    CriteriaVerification verification = new CriteriaVerification("criteria", "spec");

		// execute
	    composite.criteriaVerificationEnding(verification);

		// verify
		verifyMethodCalled("criteriaVerificationEnding", callMap1, verification);
		verifyMethodCalled("criteriaVerificationEnding", callMap2, verification);
	}


}

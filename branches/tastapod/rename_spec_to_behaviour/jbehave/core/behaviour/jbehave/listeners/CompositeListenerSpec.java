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
	private CriteriaVerification nextVerification;

	// Mocks would be great for this :(
	public class StubListener implements Listener {
		private Map methodsCalled;
		private CriteriaVerification decoratedVerification;

		public StubListener(Map methodsCalled, CriteriaVerification decoratedVerification) {
			this.methodsCalled = methodsCalled;
			this.decoratedVerification = decoratedVerification;
		}

		public void specVerificationStarting(Class spec) {
			methodsCalled.put("specVerificationStarting", spec);
		}

		public void criteriaVerificationStarting(CriteriaVerifier verifier, Object spec) {
			methodsCalled.put("criteriaVerificationStarting", verifier);
		}

		public CriteriaVerification criteriaVerificationEnding(CriteriaVerification verification, Object specInstance) {
			methodsCalled.put("criteriaVerificationEnding", verification);
			return decoratedVerification;
		}

		public void specVerificationEnding(Class spec) {
			methodsCalled.put("specVerificationEnding", spec);
		}
	}

	public void setUp() {
		composite = new CompositeListener();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		nextVerification = new CriteriaVerification("blah", "blah");
		composite.add(new StubListener(callMap1, nextVerification));
		composite.add(new StubListener(callMap2, nextVerification));
	}

	private void verifyMethodCalled(String methodName, Map callMap, Object arg) {
		Verify.equal(1, callMap.size());
		Verify.that("should of called:" + methodName, callMap.containsKey(methodName));
		Verify.equal(arg, callMap.get(methodName));
	}

	public void shouldNotifyAllListenersWhenSpecVerificationStarting() {
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
		composite.criteriaVerificationStarting(verifier, new Object());

		// verify
		verifyMethodCalled("criteriaVerificationStarting", callMap1, verifier);
		verifyMethodCalled("criteriaVerificationStarting", callMap2, verifier);

	}

	public void shouldNotifyAllListenersWhenCriteriaVerificationEndingAndPassOnReturnedCriteriaVerifierToNextListener() {
		// setup
	    CriteriaVerification verification = new CriteriaVerification("criteria", "spec");

		// execute
	    CriteriaVerification returnedVerification = composite.criteriaVerificationEnding(verification, new Object());

		// verify
		Verify.equal(nextVerification, returnedVerification);
		verifyMethodCalled("criteriaVerificationEnding", callMap1, verification);
		verifyMethodCalled("criteriaVerificationEnding", callMap2, nextVerification);
	}


}

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

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class BehaviourListenersBehaviour {
	private BehaviourListeners listeners;
	private Map callMap1;
	private Map callMap2;
	private Result nextResult;

	// Mocks would be great for this :(
	public class StubListener implements BehaviourListener {
		private Map methodsCalled;
		private Result decoratedResult;

		public StubListener(Map methodsCalled, Result decoratedResult) {
			this.methodsCalled = methodsCalled;
			this.decoratedResult = decoratedResult;
		}

		public void behaviourVerificationStarting(Behaviour behaviour) {
			methodsCalled.put("methodVerificationStarting", behaviour);
		}

		public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
			methodsCalled.put("methodVerificationEnding", result);
			return decoratedResult;
		}

        public boolean caresAbout(Behaviour behaviour) {
            return true;
        }
	}

	public void setUp() {
		listeners = new BehaviourListeners();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		nextResult = new Result("blah", Result.SUCCEEDED);
		listeners.add(new StubListener(callMap1, nextResult));
		listeners.add(new StubListener(callMap2, nextResult));
	}

	private void verifyMethodCalled(String methodName, Map callMap, Object arg) {
		Verify.equal(1, callMap.size());
		Verify.that("should have called:" + methodName, callMap.containsKey(methodName));
		Verify.equal(arg, callMap.get(methodName));
	}

	public static class SomeBehaviourClass {
	    public void shouldDoSomething() throws Exception {
        }
	}
	
	public void shouldNotifyAllListenersWhenMethodVerificationStarts() throws Exception {
		// setup
		// execute
        BehaviourMethod behaviour = new BehaviourMethod(null, null, null);
        listeners.behaviourVerificationStarting(behaviour);

		// verify
		verifyMethodCalled("methodVerificationStarting", callMap1, behaviour);
		verifyMethodCalled("methodVerificationStarting", callMap2, behaviour);

	}

	public void shouldNotifyAllListenersWhenMethodVerificationEndingAndPassOnReturnedResultToNextListener() {
		// setup
	    Result result = new Result("someMethod", Result.SUCCEEDED);

		// execute
	    Result returnedResult = listeners.behaviourVerificationEnding(result, null);

		// verify
		verifyMethodCalled("methodVerificationEnding", callMap1, result);
		verifyMethodCalled("methodVerificationEnding", callMap2, nextResult);
        Verify.equal(nextResult, returnedResult);
	}
}

/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class MethodListenersBehaviour {
	private MethodListeners listeners;
	private Map callMap1;
	private Map callMap2;
	private Result nextResult;

	// Mocks would be great for this :(
	public class StubListener implements MethodListener {
		private Map methodsCalled;
		private Result decoratedResult;

		public StubListener(Map methodsCalled, Result decoratedResult) {
			this.methodsCalled = methodsCalled;
			this.decoratedResult = decoratedResult;
		}

		public void methodVerificationStarting(Method method) {
			methodsCalled.put("methodVerificationStarting", method);
		}

		public Result methodVerificationEnding(Result result, Object behaviourClassInstance) {
			methodsCalled.put("methodVerificationEnding", result);
			return decoratedResult;
		}
	}

	public void setUp() {
		listeners = new MethodListeners();
		callMap1 = new HashMap();
		callMap2 = new HashMap();
		nextResult = new Result("blah", "blah");
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
		Method method = SomeBehaviourClass.class.getMethod("shouldDoSomething", new Class[0]);
        listeners.methodVerificationStarting(method);

		// verify
		verifyMethodCalled("methodVerificationStarting", callMap1, method);
		verifyMethodCalled("methodVerificationStarting", callMap2, method);

	}

	public void shouldNotifyAllListenersWhenMethodVerificationEndingAndPassOnReturnedResultToNextListener() {
		// setup
	    Result result = new Result("SomeBehaviourClass", "someMethod");

		// execute
	    Result returnedResult = listeners.methodVerificationEnding(result, new Object());

		// verify
		Verify.equal(nextResult, returnedResult);
		verifyMethodCalled("methodVerificationEnding", callMap1, result);
		verifyMethodCalled("methodVerificationEnding", callMap2, nextResult);
	}
}

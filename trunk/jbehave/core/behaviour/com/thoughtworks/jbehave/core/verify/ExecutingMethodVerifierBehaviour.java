/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.listeners.NULLMethodListener;
import com.thoughtworks.jbehave.util.InvokeMethodWithSetUpAndTearDown;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ExecutingMethodVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    private RecordingBehaviourListener recordingListener;
    
    public void setUp() {
        sequenceOfEvents.clear();
        recordingListener = new RecordingBehaviourListener();
    }
    
    public static class HasSucceedingMethod {
        public void shouldSucceed() {
        }
    }
    
    /** pull out the first behaviour method in a behaviour class */
    private Method firstMethod(Class behaviourClass) throws Exception {
        Method[] methods = behaviourClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No suitable method found in " + behaviourClass.getName());
    }
    
    private void verifyFirstMethod(BehaviourListener listener, Class behaviourClass) throws Exception {
        Method method = firstMethod(behaviourClass);
        Object instance = behaviourClass.newInstance();
        new BehaviourMethod(new InvokeMethodWithSetUpAndTearDown(), method, instance).verify();
    }

    public static class LogsMethodCall {
        public void shouldSucceed() throws Exception {
            sequenceOfEvents.add("shouldSucceed() called");
        }
    }
    
    public static class HasMethodThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }
    
    public static class BehaviourClassWithSetUp {
        private boolean setUpWasCalled = false;
        public void setUp() {
            setUpWasCalled = true;
        }
        public void shouldDoSomething() {
            Verify.that(setUpWasCalled);
        }
    }

    public void shouldCallSetUpBeforeVerifyingMethod() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, BehaviourClassWithSetUp.class);
        
        // verify
		Verify.that(recordingListener.latestResult.succeeded());
    }
    
    public static class BehaviourClassWithTearDown {
        public boolean tearDownWasCalled = false;
        public void tearDown() throws Exception {
            sequenceOfEvents.add("tearDown");
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldCallTearDownAfterMethodVerificationSucceeds() throws Exception {
        // setup
        // execute
        verifyFirstMethod(new NULLMethodListener(), BehaviourClassWithTearDown.class);
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
    }
    
    public static class HasTearDownAndFailingMethod extends BehaviourClassWithTearDown {
        public void shouldDoSomething() {
            Verify.impossible("Pigs flying");
        }
    }
    
    public void shouldCallTearDownAfterMethodVerificationFails() throws Exception {
        // setup
        // execute
        verifyFirstMethod(new NULLMethodListener(), HasTearDownAndFailingMethod.class);
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
	}

    public static class HasTearDownAndMethodThatThrowsException extends BehaviourClassWithTearDown {
        public void shouldDoSomething() {
            throw new RuntimeException("oh bugger");
        }
    }
    
    public void shouldCallTearDownAfterMethodThrowsException() throws Exception {
        // setup
        // execute
        verifyFirstMethod(new NULLMethodListener(), HasTearDownAndMethodThatThrowsException.class);
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
	}

    public static class BehaviourClassWithExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not enough pie");
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldNotifyListenerWhenTearDownThrowsException() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, BehaviourClassWithExceptionTearDown.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
	}

    public static class HasFailingMethodAndExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
            throw new IllegalArgumentException();
        }
    }
    
    public void shouldReportMethodExceptionIfMethodAndTearDownBothThrowExceptions() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasFailingMethodAndExceptionTearDown.class);
        // verify
        Verify.that("exception was thrown", recordingListener.latestResult.threwException());
        Verify.equal("exception type", IllegalArgumentException.class, recordingListener.latestResult.getCause().getClass());
	}
    
    public static abstract class AbstractSuperclass {
        public void shouldDoSomething() {
        }
    }
    
    public static class Subclass extends AbstractSuperclass {
    }
    
    public void shouldExecuteBehaviourMethodInheritedFromAbstractSuperclass() throws Exception {
        // when...
        verifyFirstMethod(recordingListener, Subclass.class);
        
        // then...
        Verify.equal(1, recordingListener.verifications.size());
    }
}

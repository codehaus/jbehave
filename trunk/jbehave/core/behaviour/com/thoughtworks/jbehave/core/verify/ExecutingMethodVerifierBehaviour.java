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
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.listeners.NULLMethodListener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ExecutingMethodVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    private MethodVerifier methodVerifier;
    private RecordingMethodListener recordingListener;

    public void setUp() {
        sequenceOfEvents.clear();
        methodVerifier = new ExecutingMethodVerifier();
        recordingListener = new RecordingMethodListener();
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
    
    private void verifyFirstMethod(MethodListener listener, Class behaviourClass) throws Exception {
        Method method = firstMethod(behaviourClass);
        Object instance = behaviourClass.newInstance();
        methodVerifier.verifyMethod(listener, method, instance);
    }

    public static class LogsMethodCall {
        public void shouldSucceed() throws Exception {
            sequenceOfEvents.add("shouldSucceed() called");
        }
    }
    
    public void shouldNotifyListenerBeforeMethodVerificationStarts() throws Exception {
        // setup
        MethodListener listener = new MethodListener() {
            public void methodVerificationStarting(Method method) {
                Verify.equal("shouldSucceed", method.getName());
                sequenceOfEvents.add("listener called");
            }

            public Result methodVerificationEnding(Result result, Object behaviourClassInstance) {
                return null;
            }
        };
        
        // execute
        verifyFirstMethod(listener, LogsMethodCall.class);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"listener called", "shouldSucceed() called"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }

    public void shouldNotifyListenerWhenMethodVerificationSucceeds() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasSucceedingMethod.class);
        // verify
        Verify.that(recordingListener.latestResult.succeeded());
    }

    public static class HasFailingMethod {
        public void shouldFail() {
            Verify.impossible("die die die");
        }
    }

    public void shouldNotifyListenerWhenMethodVerificationFails() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasFailingMethod.class);
        // verify
        Verify.that(recordingListener.latestResult.failed());
    }

    public static class SomeCheckedException extends Exception {}

    public static class HasMethodThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldNotifyListenerWhenMethodVerificationThrowsCheckedException() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasMethodThatThrowsCheckedException.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.instanceOf(SomeCheckedException.class, recordingListener.latestResult.getCause());
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class HasMethodThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldNotifyListenerWhenMethodThrowsRuntimeException() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasMethodThatThrowsRuntimeException.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.instanceOf(SomeRuntimeException.class, recordingListener.latestResult.getCause());
    }

    private static class SomeError extends Error {}

    public static class HasMethodThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldNotifyListenerWhenMethodThrowsError() throws Exception {
        // execute
        verifyFirstMethod(recordingListener, HasMethodThatThrowsError.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.instanceOf(SomeError.class, recordingListener.latestResult.getCause());
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
    
    public void shouldExecuteResponsibiltyMethodInheritedFromAbstractSuperclass() throws Exception {
        // when...
        verifyFirstMethod(recordingListener, Subclass.class);
        
        // then...
        Verify.equal(1, recordingListener.verifications.size());
    }
}

/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.ResponsibilityListener;
import com.thoughtworks.jbehave.core.listeners.NULLResponsibilityListener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ExecutingResponsibilityVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    private ResponsibilityVerifier responsibilityVerifier;
    private RecordingResponsibilityListener recordingListener;

    public void setUp() {
        sequenceOfEvents.clear();
        responsibilityVerifier = new ExecutingResponsibilityVerifier();
        recordingListener = new RecordingResponsibilityListener();
    }
    
    public static class BehaviourClassWithSucceedingResponsibility {
        public void shouldSucceed() {
        }
    }
    
    /** pull out the first responsibility in a behaviour class */
    private Method firstResponsibility(Class behaviourClass) throws Exception {
        Method[] methods = behaviourClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No suitable method found in " + behaviourClass.getName());
    }
    
    private void verifyFirstResponsibility(ResponsibilityListener listener, Class behaviourClass) throws Exception {
        Method method = firstResponsibility(behaviourClass);
        Object instance = behaviourClass.newInstance();
        responsibilityVerifier.verifyResponsibility(listener, method, instance);
    }

    public static class BehaviourClassWithLoggingResponsibility {
        public void shouldSucceed() throws Exception {
            sequenceOfEvents.add("shouldSucceed() called");
        }
    }
    
    public void shouldNotifyListenerBeforeResponsibilityVerificationStarts() throws Exception {
        // setup
        ResponsibilityListener listener = new ResponsibilityListener() {
            public void responsibilityVerificationStarting(Method method) {
                Verify.equal("shouldSucceed", method.getName());
                sequenceOfEvents.add("listener called");
            }

            public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
                return null;
            }
        };
        
        // execute
        verifyFirstResponsibility(listener, BehaviourClassWithLoggingResponsibility.class);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"listener called", "shouldSucceed() called"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationSucceeds() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithSucceedingResponsibility.class);
        // verify
        Verify.that(recordingListener.latestResult.succeeded());
    }

    public static class BehaviourClassWithFailingResponsibility {
        public void shouldFail() {
            Verify.impossible("die die die");
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationFails() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithFailingResponsibility.class);
        // verify
        Verify.that(recordingListener.latestResult.failed());
    }

    public static class SomeCheckedException extends Exception {}

    public static class BehaviourClassWithResponsibilityThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsCheckedException() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithResponsibilityThatThrowsCheckedException.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.that(recordingListener.latestResult.getTargetException() instanceof SomeCheckedException);
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class BehaviourClassWithResponsibilityThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsRuntimeException() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithResponsibilityThatThrowsRuntimeException.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.that(recordingListener.latestResult.getTargetException() instanceof SomeRuntimeException);
    }

    private static class SomeError extends Error {}

    public static class BehaviourClassWithResponsibilityThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsError() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithResponsibilityThatThrowsError.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
        Verify.that(recordingListener.latestResult.getTargetException() instanceof SomeError);
    }

    public static class BehaviourClassWithResponsibilityThatThrowsThreadDeath {
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

    public void shouldCallSetUpBeforeVerifyingResponsibility() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithSetUp.class);
        
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
    
    public void shouldCallTearDownAfterResponsibilityVerificationSucceeds() throws Exception {
        // setup
        // execute
        verifyFirstResponsibility(new NULLResponsibilityListener(), BehaviourClassWithTearDown.class);
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
    }
    
    public static class BehaviourClassWithTearDownAndFailingResponsibility extends BehaviourClassWithTearDown {
        public void shouldDoSomething() {
            Verify.impossible("Pigs flying");
        }
    }
    
    public void shouldCallTearDownAfterResponsibilityVerificationFails() throws Exception {
        // setup
        // execute
        verifyFirstResponsibility(new NULLResponsibilityListener(), BehaviourClassWithTearDownAndFailingResponsibility.class);
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
	}

    public static class BehaviourClassWithTearDownAndExceptionResponsibility extends BehaviourClassWithTearDown {
        public void shouldDoSomething() {
            throw new RuntimeException("oh bugger");
        }
    }
    
    public void shouldCallTearDownAfterResponsibilityVerificationThrowsException() throws Exception {
        // setup
        // execute
        verifyFirstResponsibility(new NULLResponsibilityListener(), BehaviourClassWithTearDownAndExceptionResponsibility.class);
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
        verifyFirstResponsibility(recordingListener, BehaviourClassWithExceptionTearDown.class);
        // verify
        Verify.that(recordingListener.latestResult.threwException());
	}

    public static class BehaviourClassWithFailingResponsibilityAndExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
            throw new IllegalArgumentException();
        }
    }
    
    public void shouldReportResponsibilityExceptionIfResponsibilityAndTearDownBothThrowExceptions() throws Exception {
        // execute
        verifyFirstResponsibility(recordingListener, BehaviourClassWithFailingResponsibilityAndExceptionTearDown.class);
        // verify
        Verify.that("exception was thrown", recordingListener.latestResult.threwException());
        Verify.equal("exception type", IllegalArgumentException.class, recordingListener.latestResult.getTargetException().getClass());
	}
    
    public void shouldExecuteResponsibiltyMethodInheritedFromAbstractSuperclass() throws Exception {
        // given...
        // when...
        // then...
        Verify.pending("This works but needs a method to prove it");
    }
}

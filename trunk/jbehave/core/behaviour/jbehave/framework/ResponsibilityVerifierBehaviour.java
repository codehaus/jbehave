/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbehave.listeners.ListenerSupport;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResponsibilityVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    private ResponsibilityVerifier responsibilityVerifier;

    public void setUp() {
        sequenceOfEvents.clear();
        responsibilityVerifier = new ResponsibilityVerifier();
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

    private ResponsibilityVerifier verifierForFirstResponsibility(Class behaviourClass) throws Exception {
        return new ResponsibilityVerifier();
    }

    public static class BehaviourClassWithLoggingResponsibility {
        public void shouldSucceed() throws Exception {
            sequenceOfEvents.add("shouldSucceed() called");
        }
    }
    
    public void shouldNotifyListenerBeforeResponsibilityVerificationStarts() throws Exception {
        // setup
        Method responsibilityMethod = firstResponsibility(BehaviourClassWithLoggingResponsibility.class);
        Listener listener = new ListenerSupport() {
            public void responsibilityVerificationStarting(Method responsibilityMethod) {
                Verify.equal("shouldSucceed", responsibilityMethod.getName());
                sequenceOfEvents.add("listener called");
            }
        };
        
        // execute
        responsibilityVerifier.verifyResponsibility(listener, responsibilityMethod);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"listener called", "shouldSucceed() called"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }
    
    public void shouldNotifyListenerWhenResponsibilityVerificationSucceeds() throws Exception {
        // setup
        ResponsibilityVerifier responsibilityVerifier = verifierForFirstResponsibility(BehaviourClassWithSucceedingResponsibility.class);
        RecordingListener listener = new RecordingListener();
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithSucceedingResponsibility.class));
        // verify
        Verify.that(listener.lastVerification.succeeded());
    }

    public static class BehaviourClassWithFailingResponsibility {
        public void shouldFail() {
            Verify.impossible("die die die");
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationFails() throws Exception {
        // setup
        ResponsibilityVerifier responsibilityVerifier = verifierForFirstResponsibility(BehaviourClassWithFailingResponsibility.class);
        RecordingListener listener = new RecordingListener();
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithFailingResponsibility.class));
        // verify
        Verify.that(listener.lastVerification.failed());
    }

    public static class SomeCheckedException extends Exception {}

    public static class BehaviourClassWithResponsibilityThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsCheckedException() throws Exception {
        // setup
        ResponsibilityVerifier responsibilityVerifier =
            verifierForFirstResponsibility(BehaviourClassWithResponsibilityThatThrowsCheckedException.class);
        RecordingListener listener = new RecordingListener();
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithResponsibilityThatThrowsCheckedException.class));
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeCheckedException);
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class BehaviourClassWithResponsibilityThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsRuntimeException() throws Exception {
        // setup
        ResponsibilityVerifier responsibilityVerifier = 
            verifierForFirstResponsibility(BehaviourClassWithResponsibilityThatThrowsRuntimeException.class);
        RecordingListener listener = new RecordingListener();
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithResponsibilityThatThrowsRuntimeException.class));
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeRuntimeException);
    }

    private static class SomeError extends Error {}

    public static class BehaviourClassWithResponsibilityThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldNotifyListenerWhenResponsibilityVerificationThrowsError() throws Exception {
        // setup
        ResponsibilityVerifier responsibilityVerifier =
            verifierForFirstResponsibility(BehaviourClassWithResponsibilityThatThrowsError.class);
        RecordingListener listener = new RecordingListener();
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithResponsibilityThatThrowsError.class));
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeError);
    }

    public static class BehaviourClassWithResponsibilityThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }

    public void shouldPropagateThreadDeath() throws Exception {
        ResponsibilityVerifier verifier = verifierForFirstResponsibility(BehaviourClassWithResponsibilityThatThrowsThreadDeath.class);
        try {
            verifier.verifyResponsibility(Listener.NULL, firstResponsibility(BehaviourClassWithResponsibilityThatThrowsThreadDeath.class));
            Verify.impossible("Should have thrown a ThreadDeath");
        }
        catch (ThreadDeath expected) { // you should never, ever do this :)
            // ...but what if it really was a thread death?? I mean just exactly then?
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
        // setup
        ResponsibilityVerifier verifier = verifierForFirstResponsibility(BehaviourClassWithSetUp.class);
        RecordingListener listener = new RecordingListener();
        
        // execute
        verifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithSetUp.class));
        
        // verify
		Verify.that(listener.lastVerification.succeeded());
    }
    
    private static class BehaviourClassWithTearDown {
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
        responsibilityVerifier.verifyResponsibility(Listener.NULL, firstResponsibility(BehaviourClassWithTearDown.class));
        // verify
        Verify.that(sequenceOfEvents.contains("tearDown"));
    }
    
    private static class BehaviourClassWithTearDownAndFailingResponsibility extends BehaviourClassWithTearDown {
        public void shouldDoSomething() {
            Verify.impossible("Pigs flying");
        }
    }
    
    public void shouldCallTearDownAfterResponsibilityVerificationFails() throws Exception {
        // setup
        // execute
        responsibilityVerifier.verifyResponsibility(Listener.NULL, firstResponsibility(BehaviourClassWithTearDownAndFailingResponsibility.class));
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
        responsibilityVerifier.verifyResponsibility(Listener.NULL, firstResponsibility(BehaviourClassWithTearDownAndExceptionResponsibility.class));
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
		// setup
        RecordingListener listener = new RecordingListener();
        ResponsibilityVerifier responsibilityVerifier = verifierForFirstResponsibility(BehaviourClassWithExceptionTearDown.class);
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithExceptionTearDown.class));
        // verify
        Verify.that(listener.lastVerification.threwException());
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
		// setup
        RecordingListener listener = new RecordingListener();
        ResponsibilityVerifier responsibilityVerifier = verifierForFirstResponsibility(BehaviourClassWithFailingResponsibilityAndExceptionTearDown.class);
        // execute
        responsibilityVerifier.verifyResponsibility(listener, firstResponsibility(BehaviourClassWithFailingResponsibilityAndExceptionTearDown.class));
        // verify
        Verify.that("exception was thrown", listener.lastVerification.threwException());
        Verify.equal("exception type", IllegalArgumentException.class, listener.lastVerification.getTargetException().getClass());
	}
}

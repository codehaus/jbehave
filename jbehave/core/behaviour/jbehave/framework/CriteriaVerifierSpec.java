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

import jbehave.listeners.NullListener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CriteriaVerifierSpec {

    public static class SpecWithSucceedingCriteria {
        public void shouldSucceed() {
        }
    }
    
    /** pull out the first criteria method in a spec */
    private Method firstCriteria(Class spec) throws Exception {
        Method[] methods = spec.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No spec method found in " + spec.getName());
    }

    private CriteriaVerifier verifierForFirstCriteria(Class spec) throws Exception {
        return new CriteriaVerifier(firstCriteria(spec));
    }

    public void shouldNotifyListenerBeforeCriteriaVerificationStarts() throws Exception {
        // setup
        final List sequenceOfEvents = new ArrayList(); // we check this later
        
        Method criteria = firstCriteria(SpecWithSucceedingCriteria.class);
        Object specInstance = new SpecWithSucceedingCriteria() {
            public void shouldSucceed() {
                sequenceOfEvents.add("shouldSucceed() called");
            }
        };
        final CriteriaVerifier criteriaVerifier = new CriteriaVerifier(criteria, specInstance);
        Listener listener = new NullListener() {
            public void criteriaVerificationStarting(CriteriaVerifier verifier) {
                Verify.sameInstance(criteriaVerifier, verifier);
                sequenceOfEvents.add("listener called");
            }
        };
        
        // execute
        criteriaVerifier.verifyCriteria(listener);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"listener called", "shouldSucceed() called"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }
    
    public void shouldNotifyListenerWhenCriteriaVerificationSucceeds() throws Exception {
        // setup
        CriteriaVerifier criteriaVerifier = verifierForFirstCriteria(SpecWithSucceedingCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.succeeded());
    }

    public static class SpecWithFailingCriteria {
        public void shouldFail() {
            Verify.impossible("die die die");
        }
    }

    public void shouldNotifyListenerWhenCriteriaVerificationFails() throws Exception {
        // setup
        CriteriaVerifier criteriaVerifier = verifierForFirstCriteria(SpecWithFailingCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.failed());
    }

    public static class SomeCheckedException extends Exception {}

    public static class SpecWithCriteriaThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldNotifyListenerWhenCriteriaVerificationThrowsCheckedException() throws Exception {
        // setup
        CriteriaVerifier criteriaVerifier =
            verifierForFirstCriteria(SpecWithCriteriaThatThrowsCheckedException.class);
        RecordingListener listener = new RecordingListener();
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeCheckedException);
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class SpecWithCriteriaThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldNotifyListenerWhenCriteriaVerificationThrowsRuntimeException() throws Exception {
        // setup
        CriteriaVerifier criteriaVerifier =
            verifierForFirstCriteria(SpecWithCriteriaThatThrowsRuntimeException.class);
        RecordingListener listener = new RecordingListener();
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeRuntimeException);
    }

    private static class SomeError extends Error {}

    public static class SpecWithCriteriaThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldNotifyListenerWhenCriteriaVerificationThrowsError() throws Exception {
        // setup
        CriteriaVerifier criteriaVerifier =
            verifierForFirstCriteria(SpecWithCriteriaThatThrowsError.class);
        RecordingListener listener = new RecordingListener();
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.threwException());
        Verify.that(listener.lastVerification.getTargetException() instanceof SomeError);
    }

    public static class SpecWithCriteriaThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }

    public void shouldPropagateThreadDeath() throws Exception {
        CriteriaVerifier verifier = verifierForFirstCriteria(SpecWithCriteriaThatThrowsThreadDeath.class);
        try {
            verifier.verifyCriteria(Listener.NULL);
            Verify.impossible("Should have thrown a ThreadDeath");
        }
        catch (ThreadDeath expected) { // you should never, ever do this :)
            // ...but what if it really was a thread death?? I mean just exactly then?
        }
    }
    
    public static class SpecWithSetUp {
        private boolean setUpWasCalled = false;
        public void setUp() {
            setUpWasCalled = true;
        }
        public void shouldDoSomething() {
            Verify.that(setUpWasCalled);
        }
    }

    public void shouldCallSetUpBeforeVerifyingCriteria() throws Exception {
        // setup
        CriteriaVerifier verifier = verifierForFirstCriteria(SpecWithSetUp.class);
        RecordingListener listener = new RecordingListener();
        
        // execute
        verifier.verifyCriteria(listener);
        
        // verify
		Verify.that(listener.lastVerification.succeeded());
    }
    
    private static class SpecWithTearDown {
        public boolean tearDownWasCalled = false;
        public void tearDown() throws Exception {
            tearDownWasCalled = true;
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationSucceeds() throws Exception {
        // setup
        SpecWithTearDown specInstance = new SpecWithTearDown();
    	CriteriaVerifier verifier =
            new CriteriaVerifier(firstCriteria(SpecWithTearDown.class), specInstance);
        // execute
        verifier.verifyCriteria(Listener.NULL);
        // verify
        Verify.that(specInstance.tearDownWasCalled);
    }
    
    private static class SpecWithTearDownAndFailingCriteria extends SpecWithTearDown {
        public void shouldDoSomething() {
            Verify.impossible("Pigs flying");
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationFails() throws Exception {
        // setup
        SpecWithTearDown specInstance = new SpecWithTearDownAndFailingCriteria();
        CriteriaVerifier verifier =
            new CriteriaVerifier(firstCriteria(SpecWithTearDownAndFailingCriteria.class), specInstance);
        // execute
        verifier.verifyCriteria(Listener.NULL);
        // verify
        Verify.that(specInstance.tearDownWasCalled);
	}

    public static class SpecWithTearDownAndExceptionCriteria extends SpecWithTearDown {
        public void shouldDoSomething() {
            throw new RuntimeException("oh bugger");
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationThrowsException() throws Exception {
        // setup
        SpecWithTearDown specInstance = new SpecWithTearDownAndExceptionCriteria();
        CriteriaVerifier verifier =
            new CriteriaVerifier(firstCriteria(SpecWithTearDownAndExceptionCriteria.class), specInstance);
        // execute
        verifier.verifyCriteria(Listener.NULL);
        // verify
        Verify.that(specInstance.tearDownWasCalled);
	}

    public static class SpecWithExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not enough pie");
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldNotifyListenerWhenTearDownThrowsException() throws Exception {
		// setup
        RecordingListener listener = new RecordingListener();
        CriteriaVerifier criteriaVerifier = verifierForFirstCriteria(SpecWithExceptionTearDown.class);
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that(listener.lastVerification.threwException());
	}

    public static class SpecWithFailingCriteriaAndExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
            throw new IllegalArgumentException();
        }
    }
    
    public void shouldReportCriteriaExceptionIfCriteriaAndTearDownBothThrowExceptions() throws Exception {
		// setup
        RecordingListener listener = new RecordingListener();
        CriteriaVerifier criteriaVerifier = verifierForFirstCriteria(SpecWithFailingCriteriaAndExceptionTearDown.class);
        // execute
        criteriaVerifier.verifyCriteria(listener);
        // verify
        Verify.that("exception was thrown", listener.lastVerification.threwException());
        Verify.equal("exception type", IllegalArgumentException.class, listener.lastVerification.getTargetException().getClass());
	}
}

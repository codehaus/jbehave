/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CriteriaSpec {
    private final static List results = new ArrayList();

    public void setUp() throws Exception {
        results.clear();
    }

    public static class SpecWithCriterionThatShouldSucceed {
        public void shouldSucceed() {
            results.add("success");
        }
    }

    private CriteriaVerifier getSingleCriteria(Class spec) {
        return (CriteriaVerifier)new CriteriaExtractor(spec).extractCriteria().iterator().next();
    }

    public void shouldRecogniseWhenCriteriaValidationSucceeds() throws Exception {
        // setup
        CriteriaVerifier verifier = getSingleCriteria(SpecWithCriterionThatShouldSucceed.class);
        
        // execute
        CriteriaVerificationResult result = verifier.verifyCriteria();

        // verify
        Verify.equal("shouldSucceed", result.getName());
        Verify.equal(CriteriaVerificationResult.SUCCESS, result.getStatus());
        Verify.equal(Arrays.asList(new String[] {"success"}), results);
    }

    public static class BehaviourClassWithFailingBehaviour {
        public void shouldFail() {
            Verify.impossible(null);
        }
    }

    public void shouldRecogniseWhenBehaviourMethodFails() throws Exception {
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithFailingBehaviour.class);
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        Verify.equal("shouldFail", result.getName());
        Verify.equal(CriteriaVerificationResult.FAILURE, result.getStatus());
        Verify.equal(VerificationException.class, result.getTargetException().getClass());
    }

    public static class SomeCheckedException extends Exception {}

    public static class BehaviourClassWithBehaviourThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsCheckedException() throws Exception {
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithBehaviourThatThrowsCheckedException.class);
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        
        Verify.equal("shouldThrowCheckedException", result.getName());
        Verify.equal(CriteriaVerificationResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeCheckedException.class, result.getTargetException().getClass());
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class BehaviourClassWithBehaviourThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsRuntimeException() throws Exception {
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithBehaviourThatThrowsRuntimeException.class);
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        Verify.equal("shouldThrowRuntimeException", result.getName());
        Verify.equal(CriteriaVerificationResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeRuntimeException.class, result.getTargetException().getClass());
    }

    public static class SomeError extends Error {}

    public static class BehaviourClassWithBehaviourThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsError() throws Exception {
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithBehaviourThatThrowsError.class);
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        Verify.equal("shouldThrowError", result.getName());
        Verify.equal(CriteriaVerificationResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeError.class, result.getTargetException().getClass());
    }

    public static class BehaviourClassWithBehaviourThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }

    public void shouldPropagateThreadDeath() throws Exception {
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithBehaviourThatThrowsThreadDeath.class);
        try {
            behaviour.verifyCriteria();
            Verify.impossible("Should have thrown a ThreadDeath");
        }
        catch (ThreadDeath e) { // you should never, ever do this :)
            // expected
            // ...but what if it really /was/ a thread death?? I mean just exactly then?
        }
    }

    public static class BehaviourClassWithSetUp {
        private int i = 0;

        public void setUp() {
            i = 1;
        }

        public void shouldDoSomething() {
            Verify.equal(1, i);
        }
    }

    public void shouldCallSetUpBeforeBehaviourMethodRuns() throws Exception {
        // setup
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithSetUp.class);
        
        // execute
        CriteriaVerificationResult run = behaviour.verifyCriteria();
        
        // verify
		Verify.that(run.succeeded());
    }
    
    public static class BehaviourClassWithTearDown {

        public void tearDown() throws Exception {
            results.add("hello");
        }

        public void shouldDoSomething() {
        }
    }
    
    public void shouldCallTearDownAfterBehaviourMethodSucceeds() throws Exception {
        // setup
    	CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
    }
    
    public static class FailingBehaviourClassWithTearDown extends BehaviourClassWithTearDown {
    	
        public void shouldDoSomething() {
        	Verify.impossible("Should throw VerificationException");
        }
    }
    
    public void shouldCallTearDownAfterBehaviourMethodFails() throws Exception {
        // setup
        CriteriaVerifier behaviour = getSingleCriteria(FailingBehaviourClassWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
	}

    public static class ExceptionBehaviourClassWithTearDown extends BehaviourClassWithTearDown {
        
        public void shouldDoSomething() {
            throw new RuntimeException("oh bugger");
        }
    }
    
    public void shouldCallTearDownAfterBehaviourMethodThrowsException() throws Exception {
        // setup
        CriteriaVerifier behaviour = getSingleCriteria(ExceptionBehaviourClassWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
	}

    public static class BehaviourClassWithExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldRecogniseWhenTearDownThrowsException() throws Exception {
		// setup
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithExceptionTearDown.class);
        // execute
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        // verify
        Verify.that(result.exceptionThrown());
	}

    public static class BehaviourClassWithFailingBehaviourAndExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
            throw new IllegalArgumentException();
        }
    }
    
    public void shouldReportBehaviourErrorIfBehaviourFailsAndTearDownThrowsException() throws Exception {
		// setup
        CriteriaVerifier behaviour = getSingleCriteria(BehaviourClassWithFailingBehaviourAndExceptionTearDown.class);
        // execute
        CriteriaVerificationResult result = behaviour.verifyCriteria();
        // verify
        Verify.that("exception was thrown", result.exceptionThrown());
        Verify.equal("exception type", IllegalArgumentException.class, result.getTargetException().getClass());
	}
}

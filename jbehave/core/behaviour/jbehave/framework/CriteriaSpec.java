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

    public static class SpecWithCriteriaThatShouldSucceed {
        public void shouldSucceed() {
            results.add("success");
        }
    }

    private CriteriaVerifier getSingleVerifier(Class spec) {
        return (CriteriaVerifier)new CriteriaExtractor(spec).createCriteriaVerifiers().iterator().next();
    }

    public void shouldRecogniseWhenCriteriaValidationSucceeds() throws Exception {
        // setup
        CriteriaVerifier verifier = getSingleVerifier(SpecWithCriteriaThatShouldSucceed.class);
        
        // execute
        CriteriaVerification verification = verifier.verifyCriteria();

        // verify
        Verify.equal("shouldSucceed", verification.getName());
        Verify.equal(CriteriaVerification.SUCCESS, verification.getStatus());
        Verify.equal(Arrays.asList(new String[] {"success"}), results);
    }

    public static class SpecWithFailingCriteria {
        public void shouldFail() {
            Verify.impossible(null);
        }
    }

    public void shouldRecogniseWhenCriteriaVerificationFails() throws Exception {
        CriteriaVerifier verifier = getSingleVerifier(SpecWithFailingCriteria.class);
        CriteriaVerification verification = verifier.verifyCriteria();
        Verify.equal("shouldFail", verification.getName());
        Verify.equal(CriteriaVerification.FAILURE, verification.getStatus());
        Verify.equal(VerificationException.class, verification.getTargetException().getClass());
    }

    public static class SomeCheckedException extends Exception {}

    public static class SpecWithCriteriaThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldRecogniseWhenCriteriaVerificationThrowsCheckedException() throws Exception {
        CriteriaVerifier verifier = getSingleVerifier(SpecWithCriteriaThatThrowsCheckedException.class);
        CriteriaVerification verification = verifier.verifyCriteria();
        
        Verify.equal("shouldThrowCheckedException", verification.getName());
        Verify.equal(CriteriaVerification.EXCEPTION_THROWN, verification.getStatus());
        Verify.equal(SomeCheckedException.class, verification.getTargetException().getClass());
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class SpecWithCriteriaThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldRecogniseWhenCriteriaVerificationThrowsRuntimeException() throws Exception {
        CriteriaVerifier verifier = getSingleVerifier(SpecWithCriteriaThatThrowsRuntimeException.class);
        CriteriaVerification verification = verifier.verifyCriteria();
        Verify.equal("shouldThrowRuntimeException", verification.getName());
        Verify.equal(CriteriaVerification.EXCEPTION_THROWN, verification.getStatus());
        Verify.equal(SomeRuntimeException.class, verification.getTargetException().getClass());
    }

    public static class SomeError extends Error {}

    public static class SpecWithCriteriaThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldRecogniseWhenCriteriaVerificationThrowsError() throws Exception {
        CriteriaVerifier verifier = getSingleVerifier(SpecWithCriteriaThatThrowsError.class);
        CriteriaVerification verification = verifier.verifyCriteria();
        Verify.equal("shouldThrowError", verification.getName());
        Verify.equal(CriteriaVerification.EXCEPTION_THROWN, verification.getStatus());
        Verify.equal(SomeError.class, verification.getTargetException().getClass());
    }

    public static class SpecWithCriteriaThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }

    public void shouldPropagateThreadDeath() throws Exception {
        CriteriaVerifier verifier = getSingleVerifier(SpecWithCriteriaThatThrowsThreadDeath.class);
        try {
            verifier.verifyCriteria();
            Verify.impossible("Should have thrown a ThreadDeath");
        }
        catch (ThreadDeath e) { // you should never, ever do this :)
            // expected
            // ...but what if it really /was/ a thread death?? I mean just exactly then?
        }
    }

    public static class SpecWithSetUp {
        private int i = 0;

        public void setUp() {
            i = 1;
        }

        public void shouldDoSomething() {
            Verify.equal(1, i);
        }
    }

    public void shouldCallSetUpBeforeVerifyingCriteria() throws Exception {
        // setup
        CriteriaVerifier criteria = getSingleVerifier(SpecWithSetUp.class);
        
        // execute
        CriteriaVerification run = criteria.verifyCriteria();
        
        // verify
		Verify.that(run.succeeded());
    }
    
    public static class SpecWithTearDown {

        public void tearDown() throws Exception {
            results.add("hello");
        }

        public void shouldDoSomething() {
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationSucceeds() throws Exception {
        // setup
    	CriteriaVerifier behaviour = getSingleVerifier(SpecWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
    }
    
    public static class FailingSpecWithTearDown extends SpecWithTearDown {
    	
        public void shouldDoSomething() {
        	Verify.impossible("Should throw VerificationException");
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationFails() throws Exception {
        // setup
        CriteriaVerifier behaviour = getSingleVerifier(FailingSpecWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
	}

    public static class ExceptionSpecWithTearDown extends SpecWithTearDown {
        
        public void shouldDoSomething() {
            throw new RuntimeException("oh bugger");
        }
    }
    
    public void shouldCallTearDownAfterCriteriaVerificationThrowsException() throws Exception {
        // setup
        CriteriaVerifier behaviour = getSingleVerifier(ExceptionSpecWithTearDown.class);
        // execute
        behaviour.verifyCriteria();
        // verify
        Verify.equal("hello", results.get(0));
	}

    public static class SpecWithExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
        }
    }
    
    public void shouldRecogniseWhenTearDownThrowsException() throws Exception {
		// setup
        CriteriaVerifier behaviour = getSingleVerifier(SpecWithExceptionTearDown.class);
        // execute
        CriteriaVerification verification = behaviour.verifyCriteria();
        // verify
        Verify.that(verification.threwException());
	}

    public static class SpecWithFailingCriteriaAndExceptionTearDown {
        public void tearDown() throws Exception {
            throw new Exception("Not running on an AS400");
        }
        public void shouldDoSomething() {
            throw new IllegalArgumentException();
        }
    }
    
    public void shouldReportVerificationExceptionIfVerificationAndTearDownBothThrowExceptions() throws Exception {
		// setup
        CriteriaVerifier verifier = getSingleVerifier(SpecWithFailingCriteriaAndExceptionTearDown.class);
        // execute
        CriteriaVerification verification = verifier.verifyCriteria();
        // verify
        Verify.that("exception was thrown", verification.threwException());
        Verify.equal("exception type", IllegalArgumentException.class, verification.getTargetException().getClass());
	}
}

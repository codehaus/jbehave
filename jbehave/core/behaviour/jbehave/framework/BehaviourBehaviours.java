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
public class BehaviourBehaviours {
    private final static List results = new ArrayList();

    public void setUp() throws Exception {
        results.clear();
    }

    public static class BehaviourClassWithSucceedingBehaviour {
        public void shouldSucceed() {
            results.add("hello");
        }
    }

    private Behaviour getSingleBehaviour(Class behaviourClass) {
        return (Behaviour)BehavioursSupport.getBehaviours(behaviourClass).iterator().next();
    }

    public void shouldRecogniseWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithSucceedingBehaviour.class);
        
        // execute
        BehaviourResult result = behaviour.run();

        // verify
        Verify.equal("shouldSucceed", result.getName());
        Verify.equal(BehaviourResult.SUCCESS, result.getStatus());
        Verify.equal(Arrays.asList(new String[] {"hello"}), results);
    }

    public static class BehaviourClassWithFailingBehaviour extends BehavioursSupport {
        public void shouldFail() {
            Verify.impossible(null);
        }
    }

    public void shouldRecogniseWhenBehaviourMethodFails() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithFailingBehaviour.class);
        BehaviourResult result = behaviour.run();
        Verify.equal("shouldFail", result.getName());
        Verify.equal(BehaviourResult.FAILURE, result.getStatus());
        Verify.equal(VerificationException.class, result.getTargetException().getClass());
    }

    public static class SomeCheckedException extends Exception {}

    public static class BehaviourClassWithBehaviourThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsCheckedException() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsCheckedException.class);
        BehaviourResult result = behaviour.run();
        
        Verify.equal("shouldThrowCheckedException", result.getName());
        Verify.equal(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeCheckedException.class, result.getTargetException().getClass());
    }

    public static class SomeRuntimeException extends RuntimeException {}

    public static class BehaviourClassWithBehaviourThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsRuntimeException() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsRuntimeException.class);
        BehaviourResult result = behaviour.run();
        Verify.equal("shouldThrowRuntimeException", result.getName());
        Verify.equal(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeRuntimeException.class, result.getTargetException().getClass());
    }

    public static class SomeError extends Error {}

    public static class BehaviourClassWithBehaviourThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }

    public void shouldRecogniseWhenBehaviourMethodThrowsError() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsError.class);
        BehaviourResult result = behaviour.run();
        Verify.equal("shouldThrowError", result.getName());
        Verify.equal(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        Verify.equal(SomeError.class, result.getTargetException().getClass());
    }

    public static class BehaviourClassWithBehaviourThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }

    public void shouldPropagateThreadDeath() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsThreadDeath.class);
        try {
            behaviour.run();
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
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithSetUp.class);
        
        // execute
        BehaviourResult run = behaviour.run();
        
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
    	Behaviour behaviour = getSingleBehaviour(BehaviourClassWithTearDown.class);
        // execute
        behaviour.run();
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
        Behaviour behaviour = getSingleBehaviour(FailingBehaviourClassWithTearDown.class);
        // execute
        behaviour.run();
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
        Behaviour behaviour = getSingleBehaviour(ExceptionBehaviourClassWithTearDown.class);
        // execute
        behaviour.run();
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
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithExceptionTearDown.class);
        // execute
        BehaviourResult result = behaviour.run();
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
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithFailingBehaviourAndExceptionTearDown.class);
        // execute
        BehaviourResult result = behaviour.run();
        // verify
        Verify.that("exception was thrown", result.exceptionThrown());
        Verify.equal("exception type", IllegalArgumentException.class, result.getTargetException().getClass());
	}
}

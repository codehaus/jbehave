/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.framework;

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourTest extends TestCase {
    private final static List results = new ArrayList();
    
    protected void setUp() throws Exception {
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
    
    public void testShouldRecogniseWhenBehaviourMethodSucceeds() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithSucceedingBehaviour.class);
        BehaviourResult result = behaviour.run();
        
        // assert success
        assertEquals("shouldSucceed", result.getName());
        assertEquals(BehaviourResult.SUCCESS, result.getStatus());
        
        // assert method ran
        assertEquals(1, results.size());
        assertEquals("hello", results.get(0));
    }
    
    public static class BehaviourClassWithFailingBehaviour extends BehavioursSupport {
        public void shouldFail() {
            Verify.impossible(null);
        }
    }
    
    public void testShouldRecogniseWhenBehaviourMethodFailsAssertion() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithFailingBehaviour.class);
        BehaviourResult result = behaviour.run();
        assertEquals("shouldFail", result.getName());
        assertEquals(BehaviourResult.ASSERTION_FAILED, result.getStatus());
        assertEquals(AssertionFailedError.class, result.getTargetException().getClass());
    }
    
    public static class SomeCheckedException extends Exception {}
    
    public static class BehaviourClassWithBehaviourThatThrowsCheckedException {
        public void shouldThrowCheckedException() throws Exception {
            throw new SomeCheckedException();
        }
    }
    
    public void testShouldRecogniseWhenBehaviourMethodThrowsCheckedException() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsCheckedException.class);
        BehaviourResult result = behaviour.run();
        assertEquals("shouldThrowCheckedException", result.getName());
        assertEquals(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        assertEquals(SomeCheckedException.class, result.getTargetException().getClass());
    }
    
    public static class SomeRuntimeException extends RuntimeException {}
    
    public static class BehaviourClassWithBehaviourThatThrowsRuntimeException {
        public void shouldThrowRuntimeException() {
            throw new SomeRuntimeException();
        }
    }
    
    public void testShouldRecogniseWhenBehaviourMethodThrowsRuntimeException() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsRuntimeException.class);
        BehaviourResult result = behaviour.run();
        assertEquals("shouldThrowRuntimeException", result.getName());
        assertEquals(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        assertEquals(SomeRuntimeException.class, result.getTargetException().getClass());
    }
    
    public static class SomeError extends Error {}
    
    public static class BehaviourClassWithBehaviourThatThrowsError {
        public void shouldThrowError() {
            throw new SomeError();
        }
    }
    
    public void testShouldRecogniseWhenBehaviourMethodThrowsError() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsError.class);
        BehaviourResult result = behaviour.run();
        assertEquals("shouldThrowError", result.getName());
        assertEquals(BehaviourResult.EXCEPTION_THROWN, result.getStatus());
        assertEquals(SomeError.class, result.getTargetException().getClass());
    }
    
    public static class BehaviourClassWithBehaviourThatThrowsThreadDeath {
        public void shouldThrowThreadDeath() {
            throw new ThreadDeath();
        }
    }
    
    public void testShouldPropagateThreadDeath() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithBehaviourThatThrowsThreadDeath.class);
        try {
            behaviour.run();
            fail("Should have thrown a ThreadDeath");
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
            assertEquals(1, i);
        }
    }
    
    public void testShouldCallSetUpBeforeBehaviourMethodRuns() throws Exception {
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithSetUp.class);
        assertTrue(behaviour.run().succeeded());
    }
}

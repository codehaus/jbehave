/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbehave.framework.Behaviour;
import jbehave.framework.BehaviourResult;
import jbehave.framework.BehavioursSupport;
import jbehave.runner.listener.ListenerSupport;
import junit.framework.TestCase;

/**
 * Test the {@link BehaviourRunner} class
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourRunnerTest extends TestCase {
    private final static List results = new ArrayList(); // handy place to store results
    private BehaviourRunner runner;
    
    protected void setUp() {
        runner = new BehaviourRunner();
        results.clear();
    }

    public static class BehaviourClassWithOneBehaviour extends BehavioursSupport {
        public void shouldDoSomething() {
        }
    }
    
    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoOneThing() {
        }
        public void shouldDoAnotherThing() {
        }
    }

    public void testShouldAddBehaviourClass() throws Exception {
        assertEquals(0, runner.countBehaviourClasses());
        assertEquals(0, runner.countBehaviours());
        
        runner.addBehaviourClass(BehaviourClassWithOneBehaviour.class);
        assertEquals(1, runner.countBehaviourClasses());
        assertEquals(1, runner.countBehaviours());
        assertEquals(BehaviourClassWithOneBehaviour.class, runner.getBehaviourClass(0));
        
        runner.addBehaviourClass(BehaviourClassWithTwoBehaviours.class);
        assertEquals(2, runner.countBehaviourClasses());
        assertEquals(3, runner.countBehaviours());
        assertEquals(BehaviourClassWithTwoBehaviours.class, runner.getBehaviourClass(1));
    }
    
    public void testShouldCountBehaviours() throws Exception {
        runner.addBehaviourClass(BehaviourClassWithOneBehaviour.class);
    }

    private static class RunStartedListener extends ListenerSupport {
        private final BehaviourRunner expectedRunner;
        private final String message;
        
        public RunStartedListener(BehaviourRunner expectedRunner, String message) {
            this.expectedRunner = expectedRunner;
            this.message = message;
        }

        public void runStarted(BehaviourRunner runner) {
            assertSame(expectedRunner, runner);
            results.add(message);
        }
    }
    
    public static class BehaviourClassThatSaysHello extends BehavioursSupport {
        public void shouldSayHello() {
            results.add("hello");
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenRunStarts() throws Exception {
        runner.registerListener(new RunStartedListener(runner, "one"));
        runner.registerListener(new RunStartedListener(runner, "two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        assertEquals(Arrays.asList(
            new String[]{"one", "two", "hello"}),
            results);
    }

    private static class RunEndedListener extends ListenerSupport {
        private final BehaviourRunner expectedRunner;
        private final String message;
        
        public RunEndedListener(BehaviourRunner expectedRunner, String message) {
            this.expectedRunner = expectedRunner;
            this.message = message;
        }
        
        public void runEnded(BehaviourRunner runner) {
            assertSame(expectedRunner, runner);
            results.add(message);
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenRunEnds() throws Exception {
        runner.registerListener(new RunEndedListener(runner, "one"));
        runner.registerListener(new RunEndedListener(runner, "two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        assertEquals(Arrays.asList(
            new String[]{"hello", "one", "two"}),
            results);
    }

    private static class BehaviourClassStartedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourClassStartedListener(String message) {
            this.message = message;
        }
        
        public void behaviourClassStarted(Class behaviourClass) {
            results.add(message + ":" + behaviourClass.getName());
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenBehaviourClassStarts() throws Exception {
        runner.registerListener(new BehaviourClassStartedListener("one"));
        runner.registerListener(new BehaviourClassStartedListener("two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        
        String expectedName = BehaviourClassThatSaysHello.class.getName();
        String[] expected = {"one:" + expectedName, "two:" + expectedName, "hello"};
        assertEquals(Arrays.asList(expected), results);
    }

    private static class BehaviourClassEndedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourClassEndedListener(String message) {
            this.message = message;
        }
        
        public void behaviourClassEnded(Class behaviourClass) {
            results.add(message + ":" + behaviourClass.getName());
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenBehaviourClassEnds() throws Exception {
        runner.registerListener(new BehaviourClassEndedListener("one"));
        runner.registerListener(new BehaviourClassEndedListener("two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        
        String expectedName = BehaviourClassThatSaysHello.class.getName();
        assertEquals(Arrays.asList(
            new String[]{
                "hello",
                "one:" + expectedName,
                "two:" + expectedName
                }),
            results);
    }
    
    private static class BehaviourStartedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourStartedListener(String message) {
            this.message = message;
        }
        
        public void behaviourStarted(Behaviour behaviour) {
            results.add(message + ":" + behaviour.getName());
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenBehaviourStarts() throws Exception {
        runner.registerListener(new BehaviourStartedListener("one"));
        runner.registerListener(new BehaviourStartedListener("two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        assertEquals(Arrays.asList(
            new String[]{"one:shouldSayHello", "two:shouldSayHello", "hello"}),
            results);
    }
    
    private static class BehaviourEndedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourEndedListener(String message) {
            this.message = message;
        }
        
        public void behaviourEnded(BehaviourResult behaviourResult) {
            results.add(message + ":" + behaviourResult.getName());
        }
    }
    
    public void testShouldNotifyListenersInOrderWhenBehaviourEnds() throws Exception {
        runner.registerListener(new BehaviourEndedListener("one"));
        runner.registerListener(new BehaviourEndedListener("two"));
        runner.addBehaviourClass(BehaviourClassThatSaysHello.class);
        runner.runBehaviours();
        assertEquals(Arrays.asList(
            new String[]{"hello", "one:shouldSayHello", "two:shouldSayHello"}),
            results);
    }
    
    public void testShouldNotifyBehaviourListenersForEveryBehaviour() throws Exception {
        runner.registerListener(new BehaviourStartedListener("started"));
        runner.registerListener(new BehaviourEndedListener("ended"));
        runner.addBehaviourClass(BehaviourClassWithTwoBehaviours.class);
        runner.runBehaviours();
        
        assertEquals(4, results.size());
        
        // we cannot guarantee the order behaviours are run in
        assertTrue(results.contains("started:shouldDoOneThing"));
        assertTrue(results.contains("ended:shouldDoOneThing"));
        
        assertTrue(results.contains("started:shouldDoAnotherThing"));
        assertTrue(results.contains("ended:shouldDoAnotherThing"));
    }
}

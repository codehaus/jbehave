/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbehave.framework.CriteriaVerificationResult;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.Verify;
import jbehave.verify.Verifier;
import jbehave.verify.listener.ListenerSupport;

/**
 * Test the {@link Verifier} class
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class VerifierSpec {
    private final static List resultList = new ArrayList(); // handy place to store results
    private Verifier verifier;
    
    public void setUp() {
        verifier = new Verifier();
        resultList.clear();
    }

    public static class BehaviourClassWithOneBehaviour {
        public void shouldDoSomething() {
        }
    }
    
    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoOneThing() {
        }
        public void shouldDoAnotherThing() {
        }
    }

    public void shouldAddBehaviourClass() throws Exception {
        Verify.equal(0, verifier.countSpecs());
        Verify.equal(0, verifier.countCriteria());
        
        verifier.addSpec(BehaviourClassWithOneBehaviour.class);
        Verify.equal(1, verifier.countSpecs());
        Verify.equal(1, verifier.countCriteria());
        Verify.equal(BehaviourClassWithOneBehaviour.class, verifier.getSpec(0));
        
        verifier.addSpec(BehaviourClassWithTwoBehaviours.class);
        Verify.equal(2, verifier.countSpecs());
        Verify.equal(3, verifier.countCriteria());
        Verify.equal(BehaviourClassWithTwoBehaviours.class, verifier.getSpec(1));
    }
    
    public void shouldCountBehaviours() throws Exception {
        verifier.addSpec(BehaviourClassWithOneBehaviour.class);
    }

    private static class RunStartedListener extends ListenerSupport {
        private final Verifier expectedRunner;
        private final String message;
        
        public RunStartedListener(Verifier expectedRunner, String message) {
            this.expectedRunner = expectedRunner;
            this.message = message;
        }

        public void verificationStarted(Verifier runner) {
            Verify.sameInstance(expectedRunner, runner);
            resultList.add(message);
        }
    }
    
    public static class SpecThatSaysHello {
        public void shouldSayHello() {
            resultList.add("hello");
        }
    }
    
    public void shouldNotifyListenersInOrderWhenRunStarts() throws Exception {
        verifier.registerListener(new RunStartedListener(verifier, "one"));
        verifier.registerListener(new RunStartedListener(verifier, "two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"one", "two", "hello"}), resultList);
    }

    private static class RunEndedListener extends ListenerSupport {
        private final Verifier expectedRunner;
        private final String message;
        
        public RunEndedListener(Verifier expectedRunner, String message) {
            this.expectedRunner = expectedRunner;
            this.message = message;
        }
        
        public void verificationEnded(Verifier runner) {
            Verify.sameInstance(expectedRunner, runner);
            resultList.add(message);
        }
    }
    
    public void shouldNotifyListenersInOrderWhenRunEnds() throws Exception {
        verifier.registerListener(new RunEndedListener(verifier, "one"));
        verifier.registerListener(new RunEndedListener(verifier, "two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"hello", "one", "two"}), resultList);
    }

    private static class BehaviourClassStartedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourClassStartedListener(String message) {
            this.message = message;
        }
        
        public void specVerificationStarted(Class behaviourClass) {
            resultList.add(message + ":" + behaviourClass.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourClassStarts() throws Exception {
        verifier.registerListener(new BehaviourClassStartedListener("one"));
        verifier.registerListener(new BehaviourClassStartedListener("two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        
        String expectedName = SpecThatSaysHello.class.getName();
        String[] expected = {"one:" + expectedName, "two:" + expectedName, "hello"};
        Verify.equal(Arrays.asList(expected), resultList);
    }

    private static class BehaviourClassEndedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourClassEndedListener(String message) {
            this.message = message;
        }
        
        public void specVerificationEnded(Class behaviourClass) {
            resultList.add(message + ":" + behaviourClass.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourClassEnds() throws Exception {
        verifier.registerListener(new BehaviourClassEndedListener("one"));
        verifier.registerListener(new BehaviourClassEndedListener("two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        
        String expectedName = SpecThatSaysHello.class.getName();
        Verify.equal(Arrays.asList(
		new String[]{
		    "hello",
		    "one:" + expectedName,
		    "two:" + expectedName
		    }), resultList);
    }
    
    private static class BehaviourStartedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourStartedListener(String message) {
            this.message = message;
        }
        
        public void beforeCriteriaVerificationStarts(CriteriaVerifier behaviour) {
            resultList.add(message + ":" + behaviour.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourStarts() throws Exception {
        verifier.registerListener(new BehaviourStartedListener("one"));
        verifier.registerListener(new BehaviourStartedListener("two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"one:shouldSayHello", "two:shouldSayHello", "hello"}), resultList);
    }
    
    private static class BehaviourEndedListener extends ListenerSupport {
        private final String message;
        
        public BehaviourEndedListener(String message) {
            this.message = message;
        }
        
        public void afterCriteriaVerificationEnds(CriteriaVerificationResult behaviourResult) {
            resultList.add(message + ":" + behaviourResult.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourEnds() throws Exception {
        verifier.registerListener(new BehaviourEndedListener("one"));
        verifier.registerListener(new BehaviourEndedListener("two"));
        verifier.addSpec(SpecThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"hello", "one:shouldSayHello", "two:shouldSayHello"}), resultList);
    }
    
    public void shouldNotifyBehaviourListenersForEveryBehaviour() throws Exception {
        verifier.registerListener(new BehaviourStartedListener("started"));
        verifier.registerListener(new BehaviourEndedListener("ended"));
        verifier.addSpec(BehaviourClassWithTwoBehaviours.class);
        verifier.verifyCriteria();
        
        Verify.equal(4, resultList.size());
        
        Verify.that(resultList.contains("started:shouldDoOneThing"));
        Verify.that(resultList.contains("ended:shouldDoOneThing"));
        
        Verify.that(resultList.contains("started:shouldDoAnotherThing"));
        Verify.that(resultList.contains("ended:shouldDoAnotherThing"));
    }
}

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

import jbehave.framework.CriteriaVerification;
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

    public static class SpecWithOneCriteria {
        public void shouldDoSomething() {
        }
    }
    
    public static class SpecWithTwoCriteria {
        public void shouldDoOneThing() {
        }
        public void shouldDoAnotherThing() {
        }
    }

    public void shouldAddSpec() throws Exception {
        Verify.equal(0, verifier.countSpecs());
        Verify.equal(0, verifier.countCriteria());
        
        verifier.addSpec(SpecWithOneCriteria.class);
        Verify.equal(1, verifier.countSpecs());
        Verify.equal(1, verifier.countCriteria());
        Verify.equal(SpecWithOneCriteria.class, verifier.getSpec(0));
        
        verifier.addSpec(SpecWithTwoCriteria.class);
        Verify.equal(2, verifier.countSpecs());
        Verify.equal(3, verifier.countCriteria());
        Verify.equal(SpecWithTwoCriteria.class, verifier.getSpec(1));
    }
    
    public void shouldCountCriteria() throws Exception {
        verifier.addSpec(SpecWithOneCriteria.class);
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
    
    public static class SpecWithCriteriaThatSaysHello {
        public void shouldSayHello() {
            resultList.add("hello");
        }
    }
    
    public void shouldNotifyListenersInOrderWhenRunStarts() throws Exception {
        verifier.registerListener(new RunStartedListener(verifier, "one"));
        verifier.registerListener(new RunStartedListener(verifier, "two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"one", "two", "hello"}), resultList);
    }

    private static class VerificationEndedListener extends ListenerSupport {
        private final Verifier expectedVerifier;
        private final String message;
        
        public VerificationEndedListener(Verifier expectedVerifier, String message) {
            this.expectedVerifier = expectedVerifier;
            this.message = message;
        }
        
        public void verificationEnded(Verifier verifier) {
            Verify.sameInstance(expectedVerifier, verifier);
            resultList.add(message);
        }
    }
    
    public void shouldNotifyListenersInOrderWhenVerificationEnds() throws Exception {
        verifier.registerListener(new VerificationEndedListener(verifier, "one"));
        verifier.registerListener(new VerificationEndedListener(verifier, "two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(new String[]{"hello", "one", "two"}), resultList);
    }

    private static class SpecVerificationStartedListener extends ListenerSupport {
        private final String message;
        
        public SpecVerificationStartedListener(String message) {
            this.message = message;
        }
        
        public void specVerificationStarted(Class spec) {
            resultList.add(message + ":" + spec.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourClassStarts() throws Exception {
        verifier.registerListener(new SpecVerificationStartedListener("one"));
        verifier.registerListener(new SpecVerificationStartedListener("two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        
        String expectedName = SpecWithCriteriaThatSaysHello.class.getName();
        String[] expected = {"one:" + expectedName, "two:" + expectedName, "hello"};
        Verify.equal(Arrays.asList(expected), resultList);
    }

    private static class SpecVerificationEndedListener extends ListenerSupport {
        private final String message;
        
        public SpecVerificationEndedListener(String message) {
            this.message = message;
        }
        
        public void specVerificationEnded(Class spec) {
            resultList.add(message + ":" + spec.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderWhenBehaviourClassEnds() throws Exception {
        verifier.registerListener(new SpecVerificationEndedListener("one"));
        verifier.registerListener(new SpecVerificationEndedListener("two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        
        String expectedName = SpecWithCriteriaThatSaysHello.class.getName();
        Verify.equal(Arrays.asList(
		new String[]{
		    "hello",
		    "one:" + expectedName,
		    "two:" + expectedName
		    }), resultList);
    }
    
    private static class BeforeCriteriaVerificationStartsListener extends ListenerSupport {
        private final String message;
        
        public BeforeCriteriaVerificationStartsListener(String message) {
            this.message = message;
        }
        
        public void beforeCriteriaVerificationStarts(CriteriaVerifier verifier) {
            resultList.add(message + ":" + verifier.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderBeforeCriteriaVerificationStarts() throws Exception {
        verifier.registerListener(new BeforeCriteriaVerificationStartsListener("one"));
        verifier.registerListener(new BeforeCriteriaVerificationStartsListener("two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(new String[]{"one:shouldSayHello", "two:shouldSayHello", "hello"}), resultList);
    }
    
    private static class AfterCriteriaVerificationEndsListener extends ListenerSupport {
        private final String message;
        
        public AfterCriteriaVerificationEndsListener(String message) {
            this.message = message;
        }
        
        public void afterCriteriaVerificationEnds(CriteriaVerification verification) {
            resultList.add(message + ":" + verification.getName());
        }
    }
    
    public void shouldNotifyListenersInOrderAfterCriteriaVerificationEnds() throws Exception {
        verifier.registerListener(new AfterCriteriaVerificationEndsListener("one"));
        verifier.registerListener(new AfterCriteriaVerificationEndsListener("two"));
        verifier.addSpec(SpecWithCriteriaThatSaysHello.class);
        verifier.verifyCriteria();
        Verify.equal(Arrays.asList(
		new String[]{"hello", "one:shouldSayHello", "two:shouldSayHello"}), resultList);
    }
    
    public void shouldNotifyBehaviourListenersForEveryCriteriaVerification() throws Exception {
        verifier.registerListener(new BeforeCriteriaVerificationStartsListener("started"));
        verifier.registerListener(new AfterCriteriaVerificationEndsListener("ended"));
        verifier.addSpec(SpecWithTwoCriteria.class);
        verifier.verifyCriteria();
        
        Verify.equal(4, resultList.size());
        
        Verify.that(resultList.contains("started:shouldDoOneThing"));
        Verify.that(resultList.contains("ended:shouldDoOneThing"));
        
        Verify.that(resultList.contains("started:shouldDoAnotherThing"));
        Verify.that(resultList.contains("ended:shouldDoAnotherThing"));
    }
}

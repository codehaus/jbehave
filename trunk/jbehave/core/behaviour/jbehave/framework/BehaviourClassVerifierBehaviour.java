/*
 * Created on 05-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifierBehaviour {
    public static class BehaviourClass1 {
        public void shouldSucceed() {
        }
    }
    
    public void shouldNotifyListenerWhenBehaviourClassVerificationStarts() throws Exception {
        // setup
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClass1.class, ResponsibilityVerifier.NULL);
        RecordingListener listener = new RecordingListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener);
        // verify
        Verify.equal(BehaviourClass1.class, listener.startedBehaviourClass);
    }
    
    public void shouldNotifyListenerWhenBehaviourClassVerificationEnds() throws Exception {
        // setup
        BehaviourClassVerifier verifier =
            new BehaviourClassVerifier(BehaviourClass1.class, ResponsibilityVerifier.NULL);
        RecordingListener listener = new RecordingListener();
        // execute
        verifier.verifyBehaviourClass(listener);
        // verify
        Verify.equal(BehaviourClass1.class, listener.endedBehaviourClass);
    }
    
    public void shouldVerifyOneResponsibility() throws Exception {
        // setup
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClass1.class,
                    new NotifyingResponsibilityVerifier());
        RecordingListener listener = new RecordingListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener);
        // verify
        Verify.equal(1, listener.verifications.size());
        Verify.equal("shouldSucceed", listener.lastVerification.getName());
        Verify.that(listener.lastVerification.succeeded());
    }

    public static class BehaviourWithTwoResponsibilities {
        public void shouldSucceed() {
        }
        public void shouldAlsoSucceed() {
        }
    }
    
    public void shouldVerifyTwoResponsibilities() throws Exception {
        // setup
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourWithTwoResponsibilities.class,
                    new NotifyingResponsibilityVerifier());
        RecordingListener listener = new RecordingListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener);
        // verify
        List expectedNames = Arrays.asList(new String[] {
                "shouldSucceed", "shouldAlsoSucceed"
        });
        Verify.equal(2, listener.verifications.size());
        // this nonsense is because we don't know what order the methods were extracted
        Verify.that(expectedNames.contains(listener.verification(0).getName()));
        Verify.that(expectedNames.contains(listener.verification(1).getName()));
    }
    
    public static class ContainerWithTwoBehaviours implements BehaviourClassContainer {
        public Class[] getBehaviourClasses() {
            return new Class[] {
                BehaviourClass1.class,
                BehaviourWithTwoResponsibilities.class
            };
        }
    }
    
    public void shouldVerifyContainedBehaviours() throws Exception {
        // setup
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(ContainerWithTwoBehaviours.class,
                    new NotifyingResponsibilityVerifier());
        RecordingListener listener = new RecordingListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener);
        // verify
        Verify.equal(3, listener.verifications.size());
    }
}

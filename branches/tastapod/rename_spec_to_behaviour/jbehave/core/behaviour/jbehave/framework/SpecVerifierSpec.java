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
public class SpecVerifierSpec {
    public static class SpecWithOneCriteria {
        public void shouldSucceed() {
        }
    }
    
    public void shouldNotifyListenerWhenSpecVerificationStarts() throws Exception {
        // setup
        SpecVerifier specVerifier = new SpecVerifier(SpecWithOneCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        specVerifier.verifySpec(listener);
        // verify
        Verify.equal(SpecWithOneCriteria.class, listener.startedSpec);
    }
    
    public void shouldNotifyListenerWhenSpecVerificationEnds() throws Exception {
        // setup
        SpecVerifier specVerifier = new SpecVerifier(SpecWithOneCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        specVerifier.verifySpec(listener);
        // verify
        Verify.equal(SpecWithOneCriteria.class, listener.endedSpec);
    }
    
    public void shouldVerifyOneCriteria() throws Exception {
        // setup
        SpecVerifier specVerifier = new SpecVerifier(SpecWithOneCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        specVerifier.verifySpec(listener);
        // verify
        Verify.equal(1, listener.verifications.size());
        Verify.equal("shouldSucceed", listener.lastVerification.getName());
        Verify.that(listener.lastVerification.succeeded());
    }

    public static class SpecWithTwoCriteria {
        public void shouldSucceed() {
        }
        public void shouldAlsoSucceed() {
        }
    }
    
    public void shouldVerifyTwoCriteria() throws Exception {
        // setup
        SpecVerifier specVerifier = new SpecVerifier(SpecWithTwoCriteria.class);
        RecordingListener listener = new RecordingListener();
        // execute
        specVerifier.verifySpec(listener);
        // verify
        List expectedCriteriaNames = Arrays.asList(new String[] {
                "shouldSucceed", "shouldAlsoSucceed"
        });
        Verify.equal(2, listener.verifications.size());
        // this nonsense is because we don't know what order the methods were extracted
        Verify.that(expectedCriteriaNames.contains(listener.verification(0).getName()));
        Verify.that(expectedCriteriaNames.contains(listener.verification(1).getName()));
    }
    
    public static class ContainerWithTwoSpecs implements SpecContainer {
        public Class[] getSpecs() {
            return new Class[] {SpecWithOneCriteria.class, SpecWithTwoCriteria.class};
        }
    }
    
    public void shouldVerifyContainedSpecs() throws Exception {
        // setup
        SpecVerifier specVerifier = new SpecVerifier(ContainerWithTwoSpecs.class);
        RecordingListener listener = new RecordingListener();
        // execute
        specVerifier.verifySpec(listener);
        // verify
        Verify.equal(3, listener.verifications.size());
    }
}

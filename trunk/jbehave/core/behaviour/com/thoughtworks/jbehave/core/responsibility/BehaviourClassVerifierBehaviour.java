/*
 * Created on 05-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.ResponsibilityListener;
import com.thoughtworks.jbehave.core.listeners.NULLBehaviourClassListener;
import com.thoughtworks.jbehave.core.listeners.NULLResponsibilityListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifierBehaviour {

    public static class BehaviourClassWithOneMethod {
        public void shouldSucceed() {
        }
    }
    
    public void shouldNotifyListenerWhenBehaviourClassVerificationStarts() throws Exception {
        // setup
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, new NULLResponsibilityVerifier());
        RecordingBehaviourClassListener listener = new RecordingBehaviourClassListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener, new NULLResponsibilityListener());
        // verify
        Verify.equal(BehaviourClassWithOneMethod.class, listener.startedBehaviourClass);
    }
    
    public void shouldNotifyListenerWhenBehaviourClassVerificationEnds() throws Exception {
        // setup
        BehaviourClassVerifier verifier =
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, new NULLResponsibilityVerifier());
        RecordingBehaviourClassListener listener = new RecordingBehaviourClassListener();
        verifier.verifyBehaviourClass(listener, new NULLResponsibilityListener());
        // verify
        Verify.equal(BehaviourClassWithOneMethod.class, listener.endedBehaviourClass);
    }
    
    private static class RecordingResponsibilityVerifier implements ResponsibilityVerifier {
        public final List methods = new ArrayList();
        
        public Result verifyResponsibility(ResponsibilityListener listener, Method method, Object instance) {
            methods.add(method);
            return new Result(instance.getClass().getName(), method.getName());
        }
    }
    
    public void shouldVerifyOneResponsibility() throws Exception {
        // setup
        final RecordingResponsibilityVerifier responsibilityVerifier = new RecordingResponsibilityVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, responsibilityVerifier);
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLResponsibilityListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                BehaviourClassWithOneMethod.class.getMethod("shouldSucceed", null)
        });
        
        Verify.equal(expectedMethods, responsibilityVerifier.methods);
    }

    public static class BehaviourClassWithTwoMethods {
        public void shouldSucceed() {
        }
        public void shouldAlsoSucceed() {
        }
    }
    
    public void shouldVerifyTwoResponsibilities() throws Exception {
        // setup
        final RecordingResponsibilityVerifier responsibilityVerifier = new RecordingResponsibilityVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClassWithTwoMethods.class, responsibilityVerifier);
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLResponsibilityListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                BehaviourClassWithTwoMethods.class.getMethod("shouldSucceed", null),
                BehaviourClassWithTwoMethods.class.getMethod("shouldAlsoSucceed", null)
        });
        
        Verify.equal(expectedMethods, responsibilityVerifier.methods);
    }
    
    public static class ContainerWithTwoBehaviours implements BehaviourClassContainer {
        public Class[] getBehaviourClasses() {
            return new Class[] {
                BehaviourClassWithOneMethod.class,
                BehaviourClassWithTwoMethods.class
            };
        }
    }
    
    public void shouldVerifyContainedBehaviours() throws Exception {
        // setup
        RecordingResponsibilityVerifier recordingResponsibilityVerifier = new RecordingResponsibilityVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(ContainerWithTwoBehaviours.class,
                    recordingResponsibilityVerifier);
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLResponsibilityListener());
        // verify
        Verify.equal(3, recordingResponsibilityVerifier.methods.size());
    }
}

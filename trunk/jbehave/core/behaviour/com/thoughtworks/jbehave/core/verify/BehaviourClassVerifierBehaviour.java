/*
 * Created on 05-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.listeners.NULLBehaviourClassListener;
import com.thoughtworks.jbehave.core.listeners.NULLMethodListener;

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
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, new NULLMethodVerifier());
        RecordingBehaviourClassListener listener = new RecordingBehaviourClassListener();
        // execute
        behaviourVerifier.verifyBehaviourClass(listener, new NULLMethodListener());
        // verify
        Verify.equal(BehaviourClassWithOneMethod.class, listener.startedBehaviourClass);
    }
    
    public void shouldNotifyListenerWhenBehaviourClassVerificationEnds() throws Exception {
        // setup
        BehaviourClassVerifier verifier =
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, new NULLMethodVerifier());
        RecordingBehaviourClassListener listener = new RecordingBehaviourClassListener();
        verifier.verifyBehaviourClass(listener, new NULLMethodListener());
        // verify
        Verify.equal(BehaviourClassWithOneMethod.class, listener.endedBehaviourClass);
    }
    
    private static class RecordingMethodVerifier implements MethodVerifier {
        public final List methods = new ArrayList();
        
        public Result verifyMethod(MethodListener listener, Method method, Object instance) {
            methods.add(method);
            return new Result(instance.getClass().getName(), method.getName());
        }
    }
    
    public void shouldVerifyOneMethod() throws Exception {
        // setup
        final RecordingMethodVerifier methodVerifier = new RecordingMethodVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClassWithOneMethod.class, methodVerifier);
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLMethodListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                BehaviourClassWithOneMethod.class.getMethod("shouldSucceed", null)
        });
        
        Verify.equal(expectedMethods, methodVerifier.methods);
    }

    public static class BehaviourClassWithTwoMethods {
        public void shouldSucceed() {
        }
        public void shouldAlsoSucceed() {
        }
    }
    
    public void shouldVerifyTwoMethods() throws Exception {
        // setup
        final RecordingMethodVerifier methodVerifier = new RecordingMethodVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(BehaviourClassWithTwoMethods.class, methodVerifier);
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLMethodListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                BehaviourClassWithTwoMethods.class.getMethod("shouldSucceed", null),
                BehaviourClassWithTwoMethods.class.getMethod("shouldAlsoSucceed", null)
        });
        
        Verify.equal(expectedMethods, methodVerifier.methods);
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
        RecordingMethodVerifier methodVerifier = new RecordingMethodVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(ContainerWithTwoBehaviours.class,
                    methodVerifier);
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener(), new NULLMethodListener());
        // verify
        Verify.equal(3, methodVerifier.methods.size());
    }
}

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
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.listeners.NULLBehaviourClassListener;
import com.thoughtworks.jbehave.util.DontInvokeMethod;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifierBehaviour {

    public static class HasOneMethod {
        public void shouldSucceed() {
        }
    }
    
    private static class RecordingBehaviourVerifier extends BehaviourVerifier {
        public RecordingBehaviourVerifier() {
            super(null);
        }

        public final List methods = new ArrayList();
        
        public Result verifyMethod(BehaviourListener listener, Method method, Object instance) {
            methods.add(method);
            return new Result(method.getName(), Result.SUCCEEDED);
        }
    }
    
    public void shouldVerifyOneMethod() throws Exception {
        // setup
        final RecordingBehaviourVerifier methodVerifier = new RecordingBehaviourVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(HasOneMethod.class, methodVerifier, new DontInvokeMethod());
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                HasOneMethod.class.getMethod("shouldSucceed", null)
        });
        
        Verify.equal(expectedMethods, methodVerifier.methods);
    }

    public static class HasTwoMethods {
        public void shouldSucceed() {
        }
        public void shouldAlsoSucceed() {
        }
    }
    
    public void shouldVerifyTwoMethods() throws Exception {
        // setup
        final RecordingBehaviourVerifier methodVerifier = new RecordingBehaviourVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(HasTwoMethods.class, methodVerifier, new DontInvokeMethod());
        
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener());
        
        // verify
        List expectedMethods = Arrays.asList(new Method[] {
                HasTwoMethods.class.getMethod("shouldSucceed", null),
                HasTwoMethods.class.getMethod("shouldAlsoSucceed", null)
        });
        
        Verify.equal(expectedMethods, methodVerifier.methods);
    }
    
    public static class ContainsTwoBehaviours implements BehaviourClassContainer {
        public Class[] getBehaviourClasses() {
            return new Class[] {
                HasOneMethod.class,
                HasTwoMethods.class
            };
        }
    }
    
    public void shouldVerifyContainedBehaviours() throws Exception {
        // setup
        RecordingBehaviourVerifier methodVerifier = new RecordingBehaviourVerifier();
        BehaviourClassVerifier behaviourVerifier =
            new BehaviourClassVerifier(ContainsTwoBehaviours.class,
                    methodVerifier, new DontInvokeMethod());
        // execute
        behaviourVerifier.verifyBehaviourClass(new NULLBehaviourClassListener());
        // verify
        Verify.equal(3, methodVerifier.methods.size());
    }
}

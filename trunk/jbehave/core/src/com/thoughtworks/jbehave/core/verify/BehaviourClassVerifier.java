/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.BehaviourClassListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.util.MethodInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifier {
    private final Class behaviourClass;
    private final BehaviourVerifier methodVerifier;
    private final MethodInvoker invoker;

    public BehaviourClassVerifier(Class behaviourClass, BehaviourVerifier methodVerifier, MethodInvoker invoker) {
        this.behaviourClass = behaviourClass;
        this.methodVerifier = methodVerifier;
        this.invoker = invoker;
    }

    public void verifyBehaviourClass(BehaviourClassListener UNUSED) {
        try {
            UNUSED.behaviourClassVerificationStarting(behaviourClass);
            if (BehaviourClassContainer.class.isAssignableFrom(behaviourClass)) {
                verifyContainedBehaviourClasses((BehaviourClassContainer) behaviourClass.newInstance(), UNUSED);
            }
            Method methods[] = behaviourClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    Object instance = behaviourClass.newInstance();
                    Behaviour behaviour = new BehaviourMethod(invoker, method, instance);
                    methodVerifier.verify(behaviour);
                }
            }
            UNUSED.behaviourClassVerificationEnding(behaviourClass);
        } catch (Exception e) {
            throw new JBehaveFrameworkError("Problem verifying behaviour class", e);
        }
    }
    
    private void verifyContainedBehaviourClasses(BehaviourClassContainer container, BehaviourClassListener behaviourClassListener) throws Exception {
        Class[] containedBehaviourClasses = container.getBehaviourClasses();
        for (int i = 0; i < containedBehaviourClasses.length; i++) {
            new BehaviourClassVerifier(containedBehaviourClasses[i], methodVerifier, invoker)
                .verifyBehaviourClass(behaviourClassListener);
        }
    }
}

/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.BehaviourClassListener;
import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifier {
    private final Class behaviourClass;
    private final MethodVerifier methodVerifier;

    public BehaviourClassVerifier(Class behaviourClass, MethodVerifier methodVerifier) {
        this.behaviourClass = behaviourClass;
        this.methodVerifier = methodVerifier;
    }

    public void verifyBehaviourClass(BehaviourClassListener behaviourClassListener, MethodListener methodListener) {
        try {
            behaviourClassListener.behaviourClassVerificationStarting(behaviourClass);
            if (BehaviourClassContainer.class.isAssignableFrom(behaviourClass)) {
                verifyContainedBehaviourClasses((BehaviourClassContainer) behaviourClass.newInstance(), behaviourClassListener, methodListener);
            }
            Method methods[] = behaviourClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    Object instance = behaviourClass.newInstance();
                    methodVerifier.verifyMethod(methodListener, method, instance);
                }
            }
            behaviourClassListener.behaviourClassVerificationEnding(behaviourClass);
        } catch (Exception e) {
            throw new JBehaveFrameworkError("Problem verifying behaviour class", e);
        }
    }
    
    private void verifyContainedBehaviourClasses(BehaviourClassContainer container, BehaviourClassListener behaviourClassListener, MethodListener  methodListener) throws Exception {
        Class[] containedBehaviourClasses = container.getBehaviourClasses();
        for (int i = 0; i < containedBehaviourClasses.length; i++) {
            new BehaviourClassVerifier(containedBehaviourClasses[i], methodVerifier)
                .verifyBehaviourClass(behaviourClassListener, methodListener);
        }
    }
}

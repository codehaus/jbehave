/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.Listener;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassVerifier {
    private final Class behaviourClass;
    private final ResponsibilityVerifier responsibilityVerifier;

    public BehaviourClassVerifier(Class behaviourClass, ResponsibilityVerifier responsibilityVerifier) {
        this.behaviourClass = behaviourClass;
        this.responsibilityVerifier = responsibilityVerifier;
    }

    public void verifyBehaviourClass(Listener listener) {
        try {
            listener.behaviourClassVerificationStarting(behaviourClass);
            if (BehaviourClassContainer.class.isAssignableFrom(behaviourClass)) {
                verifyContainedBehaviourClasses((BehaviourClassContainer) behaviourClass.newInstance(), listener);
            }
            Method methods[] = behaviourClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                Object instance = behaviourClass.newInstance();
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    responsibilityVerifier.verifyResponsibility(listener, method, instance);
                }
            }
            listener.behaviourClassVerificationEnding(behaviourClass);
        } catch (Exception e) {
            throw new JBehaveFrameworkError("Problem verifying behaviour class", e);
        }
    }
    
    private void verifyContainedBehaviourClasses(BehaviourClassContainer container, Listener listener) throws Exception {
        Class[] containedBehaviourClasses = container.getBehaviourClasses();
        for (int i = 0; i < containedBehaviourClasses.length; i++) {
            new BehaviourClassVerifier(containedBehaviourClasses[i], responsibilityVerifier)
                .verifyBehaviourClass(listener);
        }
    }
}

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
import com.thoughtworks.jbehave.core.BehaviourClassListener;
import com.thoughtworks.jbehave.core.ResponsibilityListener;
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

    public void verifyBehaviourClass(BehaviourClassListener behaviourClassListener, ResponsibilityListener responsibilityMethodListener) {
        try {
            behaviourClassListener.behaviourClassVerificationStarting(behaviourClass);
            if (BehaviourClassContainer.class.isAssignableFrom(behaviourClass)) {
                verifyContainedBehaviourClasses((BehaviourClassContainer) behaviourClass.newInstance(), behaviourClassListener, responsibilityMethodListener);
            }
            Method methods[] = behaviourClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    Object instance = behaviourClass.newInstance();
                    responsibilityVerifier.verifyResponsibility(responsibilityMethodListener, method, instance);
                }
            }
            behaviourClassListener.behaviourClassVerificationEnding(behaviourClass);
        } catch (Exception e) {
            throw new JBehaveFrameworkError("Problem verifying behaviour class", e);
        }
    }
    
    private void verifyContainedBehaviourClasses(BehaviourClassContainer container, BehaviourClassListener behaviourClassListener, ResponsibilityListener  responsibilityMethodListener) throws Exception {
        Class[] containedBehaviourClasses = container.getBehaviourClasses();
        for (int i = 0; i < containedBehaviourClasses.length; i++) {
            new BehaviourClassVerifier(containedBehaviourClasses[i], responsibilityVerifier)
                .verifyBehaviourClass(behaviourClassListener, responsibilityMethodListener);
        }
    }
}

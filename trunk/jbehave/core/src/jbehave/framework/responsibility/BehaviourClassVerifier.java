/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework.responsibility;

import java.lang.reflect.Method;

import jbehave.framework.BehaviourClassContainer;
import jbehave.framework.Listener;
import jbehave.framework.exception.BehaviourFrameworkError;

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
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    responsibilityVerifier.verifyResponsibility(listener, method);
                }
            }
            listener.behaviourClassVerificationEnding(behaviourClass);
        } catch (Exception e) {
            throw new BehaviourFrameworkError("Problem verifying behaviour class", e);
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

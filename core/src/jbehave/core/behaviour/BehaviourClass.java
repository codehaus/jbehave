/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import java.lang.reflect.Method;

import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.listener.ResultListener;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClass implements Behaviour {

    private final Class classToVerify;

    public BehaviourClass(Class classToVerify) {
        this.classToVerify = classToVerify;
    }

    public void verifyTo(ResultListener listener) {
        if (Behaviours.class.isAssignableFrom(classToVerify)) {
            verifyNestedClassesTo(listener);
        }
        verifyMethodsTo(listener);
    }

    private void verifyNestedClassesTo(ResultListener listener) {
        Behaviours behaviours = (Behaviours) createBehaviourClassInstance();
        Class[] nestedClasses = behaviours.getBehaviours();
        for (int i = 0; i < nestedClasses.length; i++) {
            new BehaviourClass(nestedClasses[i]).verifyTo(listener);
        }
    }

    private void verifyMethodsTo(ResultListener listener) {
        Method[] methods = classToVerify.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                Behaviour behaviourMethod = new BehaviourMethod(createBehaviourClassInstance(), method);
                behaviourMethod.verifyTo(listener);
            }
        }
    }

    private Object createBehaviourClassInstance() {
        try {
            return classToVerify.newInstance();
        }
        catch (Exception e) {
            throw new JBehaveFrameworkError("Unable to create instance of " + classToVerify.getName(), e);
        }
    }
    
    public String toString() {
        return "BehaviourClass: " + classToVerify.getName();
    }

    public Class classToVerify() {
        return classToVerify;
    }
}

/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClass implements Behaviour {

    private final Class classToVerify;
    private final Verifier verifier;

    public BehaviourClass(Class classToVerify, Verifier verifier) {
        this.classToVerify = classToVerify;
        this.verifier = verifier;
    }

    public Result verify(Verifier verifier) {
        return verifier.verify(this);
    }

    public void accept(Visitor visitor) {
        try {
            visitor.before(this);
            if (BehaviourClassContainer.class.isAssignableFrom(classToVerify)) {
                visitBehaviourClasses(visitor);
            }
            else {
                Method[] methods = classToVerify.getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if (method.getName().startsWith("should")
                            && method.getParameterTypes().length == 0) {
                        BehaviourMethod methodToVerify = new BehaviourMethod(
                                classToVerify.newInstance(), method, verifier);
                        methodToVerify.accept(visitor);
                    }
                }
            }
            visitor.after(this);
        }
        catch (Exception e) {
            throw new JBehaveFrameworkError("Failed to verify " + classToVerify.getName(), e);
        }
    }
    
    private void visitBehaviourClasses(Visitor visitor) {
        try {
            Class[] behaviourClasses = ((BehaviourClassContainer)classToVerify.newInstance()).getBehaviourClasses();
            for (int i = 0; i < behaviourClasses.length; i++) {
                BehaviourClass visitableClass = new BehaviourClass(behaviourClasses[i], verifier);
                visitableClass.accept(visitor);
            }
        }
        catch (Exception e) {
            throw new JBehaveFrameworkError("Unable to verify behaviour classes for " + classToVerify.getName(), e);
        }
    }

    public String toString() {
        return "VisitableClass: " + classToVerify.getName();
    }

    public Class classToVerify() {
        return classToVerify;
    }
}

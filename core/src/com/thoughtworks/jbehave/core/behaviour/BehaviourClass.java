/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClass implements Visitable {

    private final Class classToVerify;

    public BehaviourClass(Class classToVerify) {
        this.classToVerify = classToVerify;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);

        if (BehaviourClassContainer.class.isAssignableFrom(classToVerify)) {
            visitBehaviourClasses(visitor);
        }
        else {
            visitBehaviourMethods(visitor);
        }
    }
    
    private void visitBehaviourMethods(Visitor visitor) {
        Method[] methods = classToVerify.getMethods();
        for (int j = 0; j < methods.length; j++) {
            Method method = methods[j];
            if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                BehaviourMethod methodToVerify = new BehaviourMethod(createInstance(), method);
                methodToVerify.accept(visitor);
            }
        }
    }

    private Object createInstance() {
        try {
            return classToVerify.newInstance();
        }
        catch (Exception e) {
            throw new NestedVerificationException("Unable to create instance of " + classToVerify.getName(), e);
        }
    }

    private void visitBehaviourClasses(Visitor visitor) {
        try {
            Class[] behaviourClasses = ((BehaviourClassContainer)createInstance()).getBehaviourClasses();
            for (int i = 0; i < behaviourClasses.length; i++) {
                BehaviourClass visitableClass = new BehaviourClass(behaviourClasses[i]);
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

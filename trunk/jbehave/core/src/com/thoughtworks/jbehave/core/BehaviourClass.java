/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.verify.BehaviourVerifier;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.util.MethodInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClass implements Behaviour {

    private final Class classToVerify;
    private final BehaviourVerifier behaviourVerifier;
    private final MethodInvoker invoker;

    public BehaviourClass(Class classToVerify, BehaviourVerifier behaviourVerifier, MethodInvoker invoker) {
        this.classToVerify = classToVerify;
        this.behaviourVerifier = behaviourVerifier;
        this.invoker = invoker;
    }

    public Result verify() {
        Result result = new Result(classToVerify.getName(), Result.SUCCEEDED);
        try {
            if (BehaviourClassContainer.class.isAssignableFrom(classToVerify)) {
                verifyContainedBehaviourClasses((BehaviourClassContainer) classToVerify.newInstance());
            }
            Method methods[] = classToVerify.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    Object instance = classToVerify.newInstance();
                    Behaviour behaviour = new BehaviourMethod(invoker, method, instance);
                    behaviourVerifier.verify(behaviour);
                }
            }
            return result;
        } catch (Exception e) {
            throw new JBehaveFrameworkError("Problem verifying behaviour class", e);
        }
    }
    
    private void verifyContainedBehaviourClasses(BehaviourClassContainer container) throws Exception {
        Class[] containedBehaviourClasses = container.getBehaviourClasses();
        for (int i = 0; i < containedBehaviourClasses.length; i++) {
            Behaviour behaviour = new BehaviourClass(containedBehaviourClasses[i], behaviourVerifier, invoker);
            behaviourVerifier.verify(behaviour);
        }
    }
    
    public Class getClassToVerify() {
        return classToVerify;
    }
}

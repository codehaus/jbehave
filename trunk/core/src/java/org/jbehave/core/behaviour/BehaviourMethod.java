/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.behaviour;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jbehave.core.exception.JBehaveFrameworkError;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.result.BehaviourMethodResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Behaviour {

    private final Object instance;
    private final Method method;


    public BehaviourMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Method method() {
        return method;
    }

    public Object instance() {
        return instance;
    }

    public void verifyTo(BehaviourListener listener) {
        try {
            invokeBehaviourMethod();
            listener.gotResult(new BehaviourMethodResult(this));
        }
        catch (InvocationTargetException e) {
            listener.gotResult(new BehaviourMethodResult(this, e.getTargetException()));
        }
        catch (Throwable e) {
            throw new JBehaveFrameworkError("Problem verifying method " + method.getName(), e);
        }
    }
    
    private void invokeBehaviourMethod() throws Throwable {
        Exception thrownException = null;
        try {
            invokeMethod("setUp", instance);
            method.invoke(instance, new Object[0]);
        }
        catch (Exception e) {
            throw thrownException = e;
        }
        finally {
            try {
                invokeMethod("tearDown", instance);
            }
            catch (Exception problemWithTearDown) {
                throw(thrownException != null ? thrownException : problemWithTearDown);
            }
        }
    }

    private void invokeMethod(String methodName, Object behaviourClassInstance) throws Exception {
        try {
            Method target = behaviourClassInstance.getClass().getMethod(methodName, new Class[0]);
            target.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a method - never mind
        }
    }

    public int countBehaviours() {
        return 1;
    }
}

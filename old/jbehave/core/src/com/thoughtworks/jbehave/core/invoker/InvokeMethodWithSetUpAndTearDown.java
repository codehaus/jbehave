/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.behaviour.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.result.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.result.Result;

/**
 * Invoke a method along with the lifecycle methods <tt>setUp()</tt> and
 * <tt>tearDown()</tt> in the appropriate places if either of them exist.<br>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class InvokeMethodWithSetUpAndTearDown implements MethodInvoker {

    public Result invoke(BehaviourMethod behaviour) {
        try {
            invoke(behaviour.instance(), behaviour.method());
            return new BehaviourMethodResult(behaviour);
        }
        catch (InvocationTargetException e) {
            return new BehaviourMethodResult(behaviour, e.getTargetException());
        }
        catch (Throwable e) {
            throw new JBehaveFrameworkError("Problem verifying method " + behaviour.method().getName());
        }
    }

    private void invoke(Object instance, Method method) throws Throwable {
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
                throw (thrownException != null ? thrownException : problemWithTearDown);
            }
        }
    }

    private void invokeMethod(String methodName, Object behaviourClassInstance) throws IllegalAccessException, InvocationTargetException {
        try {
            Method setUp = behaviourClassInstance.getClass().getMethod(methodName, new Class[0]);
            setUp.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a method - never mind
        }
    }
}

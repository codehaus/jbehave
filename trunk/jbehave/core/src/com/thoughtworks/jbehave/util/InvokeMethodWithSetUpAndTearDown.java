/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class InvokeMethodWithSetUpAndTearDown implements MethodInvoker {
    /**
     * Invoke the method along with the lifecycle methods <tt>setUp()</tt> and
     * <tt>tearDown()</tt> in the appropriate places if either of them exist.<br>
     */
    public void invoke(Object instance, Method method) throws Throwable {
        Exception problem = null;
        try {
            invokeMethod("setUp", instance);
            method.invoke(instance, new Object[0]);
        }
        catch (Exception e) {
            throw problem = e;
        }
        finally {
            try {
                invokeMethod("tearDown", instance);
            }
            catch (Exception e) {
                throw (problem != null ? problem : e);
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

/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Behaviour {

    private final MethodInvoker methodInvoker;
    private final Method methodToVerify;
    private final Object instance;

    public BehaviourMethod(MethodInvoker methodInvoker, Method methodToVerify, Object instance) {
        this.methodInvoker = methodInvoker;
        this.methodToVerify = methodToVerify;
        this.instance = instance;
    }
    
    public Result verify() {
        Result result = null;
        String behaviourClassName = instance.getClass().getName();
        try {
            methodInvoker.invoke(instance, methodToVerify);
            result = new Result(methodToVerify.getName(), Result.SUCCEEDED);
        } catch (InvocationTargetException e) {
            // method failed
            result = createResult(behaviourClassName, methodToVerify.getName(), e.getTargetException());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new JBehaveFrameworkError(
                    "Problem invoking " + methodToVerify.getDeclaringClass().getName() + "#" + methodToVerify.getName(), e);
        }
        return result;
    }

    /**
     * Create a {@link Result}, possibly based on an error condition.
     * 
     * This will be one of the following cases:
     * <ul>
     * <li>a {@link VerificationException}, which means the verification failed.</li>
     * <li>a {@link ThreadDeath}, which should always be propagated.</li>
     * <li>some other kind of exception was thrown.</li>
     * </ul>
     * 
     * @throws ThreadDeath if the target exception itself is a <tt>ThreadDeath</tt>.
     */
    private Result createResult(String className, String methodName, Throwable targetException) {
        
        // propagate thread death otherwise Bad Things happen (or rather Good Things don't)
        if (targetException instanceof ThreadDeath) {
            throw (ThreadDeath)targetException;
        }
        else {
            return new Result(new ConvertCase(methodName).toSeparateWords(), targetException);
        }
    }
    
    public Method getMethodToVerify() {
        return methodToVerify;
    }
    
    public Object getInstance() {
        return instance;
    }
}

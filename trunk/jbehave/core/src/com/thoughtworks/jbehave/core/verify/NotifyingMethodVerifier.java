/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NotifyingMethodVerifier implements MethodVerifier {

    /**
     * Verify an individual method.
     * 
     * The {@link MethodListener} is alerted before and after the verification,
     * with calls to {@link MethodListener#methodVerificationStarting(Method)
     * methodVerificationStarting(this)} and
     * {@link MethodListener#methodVerificationEnding(Result, Object)
     * methodVerificationEnding(result)} respectively.
     */
    public Result verifyMethod(MethodListener listener, Method method, Object instance) {
        try {
            listener.methodVerificationStarting(method);
            Result result = doVerifyMethod(method, instance);
            result = listener.methodVerificationEnding(result, instance);
            return result;
        } catch (Exception e) {
            System.out.println("Problem verifying " + method);
            throw new JBehaveFrameworkError(e);
        }
    }

    protected Result doVerifyMethod(Method method, Object instance) {
        return new Result(method.getDeclaringClass().getName(), method.getName());
    }

}

/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.util.MethodInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @deprecated
 */
public class NotifyingMethodVerifier implements MethodVerifier {

    private final MethodInvoker invoker;

    public NotifyingMethodVerifier(MethodInvoker invoker) {
        this.invoker = invoker;
    }
    
    /**
     * Verify an individual method.
     * 
     * The {@link BehaviourListener} is alerted before and after the verification,
     * with calls to {@link BehaviourListener#behaviourVerificationStarting(Behaviour)
     * methodVerificationStarting(this)} and
     * {@link BehaviourListener#behaviourVerificationEnding(Result, Behaviour)
     * methodVerificationEnding(result)} respectively.
     */
    public Result verifyMethod(BehaviourListener listener, Method method, Object instance) {
        try {
            Behaviour behaviour = new BehaviourMethod(invoker, method, instance);
            Result result = new BehaviourVerifier(listener).verify(behaviour);
            return result;
        } catch (Exception e) {
            System.err.println("Problem verifying " + method);
            throw new JBehaveFrameworkError(e);
        }
    }
}

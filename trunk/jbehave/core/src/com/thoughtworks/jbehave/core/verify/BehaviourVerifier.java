/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourVerifier {
    private final BehaviourListener listener;

    public BehaviourVerifier(BehaviourListener listener) {
        this.listener = listener;
    }

    /**
     * Verify an individual behaviour and notify a listener.
     * 
     * The {@link BehaviourListener} is notified before and after the verification,
     * with calls to {@link BehaviourListener#behaviourVerificationStarting(Behaviour)
     * behaviourVerificationStarting(this)} and
     * {@link BehaviourListener#behaviourVerificationEnding(Result, Behaviour)
     * behaviourVerificationEnding(result)} respectively.
     */
    public Result verify(Behaviour behaviour) {
        try {
            listener.behaviourVerificationStarting(behaviour);
            Result result = behaviour.verify();
            result = listener.behaviourVerificationEnding(result, behaviour);
            return result;
        } catch (Exception e) {
            System.out.println("Problem verifying " + behaviour);
            throw new JBehaveFrameworkError(e);
        }
    }
}

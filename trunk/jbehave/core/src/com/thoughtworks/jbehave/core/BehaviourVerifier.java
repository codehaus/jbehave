/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourVerifier {
    private final List listeners = new ArrayList();

    public BehaviourVerifier() {
    }
    
    public void registerListener(BehaviourListener listener) {
        listeners.add(listener);
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
            behaviourVerificationStarting(behaviour);
            Result result = behaviour.verify();
            result = behaviourVerificationEnding(result, behaviour);
            return result;
        } catch (Exception e) {
            System.out.println("Problem verifying " + behaviour);
            throw new JBehaveFrameworkError(e);
        }
    }

    private Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            BehaviourListener listener = (BehaviourListener) i.next();
            if (listener.caresAbout(behaviour)) {
                result = listener.behaviourVerificationEnding(result, behaviour);
            }
        }
        return result;
    }

    private void behaviourVerificationStarting(Behaviour behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            BehaviourListener listener = (BehaviourListener) i.next();
            if (listener.caresAbout(behaviour)) {
                listener.behaviourVerificationStarting(behaviour);
            }
        }
    }
}

/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.listeners.ListenerSupport;


/** Listener that captures verification results */
class RecordingListener extends ListenerSupport {
    public Class startedBehaviourClass = null;
    public Class endedBehaviourClass = null;
    public List verifications = new ArrayList(); // all verifications
    public Result latestResult = null;
    
    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
        verifications.add(result);
        latestResult = result;
		return result;
    }

    public void behaviourClassVerificationStarting(Class behaviourClass) {
        startedBehaviourClass = behaviourClass;
    }
    
    public void behaviourClassVerificationEnding(Class behaviourClass) {
        endedBehaviourClass = behaviourClass;
    }
    
    public Result result(int i) {
        return (Result) verifications.get(i);
    }
}
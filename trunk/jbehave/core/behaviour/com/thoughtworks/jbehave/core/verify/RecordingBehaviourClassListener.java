/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import com.thoughtworks.jbehave.core.BehaviourClassListener;


/** Listener that captures verification results */
class RecordingBehaviourClassListener implements BehaviourClassListener {
    public Class startedBehaviourClass = null;
    public Class endedBehaviourClass = null;
    
    public void behaviourClassVerificationStarting(Class behaviourClass) {
        startedBehaviourClass = behaviourClass;
    }
    
    public void behaviourClassVerificationEnding(Class behaviourClass) {
        endedBehaviourClass = behaviourClass;
    }
}
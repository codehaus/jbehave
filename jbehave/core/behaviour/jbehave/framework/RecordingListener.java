/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.util.ArrayList;
import java.util.List;

import jbehave.listeners.NullListener;


/** Listener that captures verification results */
class RecordingListener extends NullListener {
    public Class startedBehaviourClass = null;
    public Class endedBehaviourClass = null;
    public List verifications = new ArrayList(); // all verifications
    public ResponsibilityVerification lastVerification = null; // latest one
    
    public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification verification, Object behaviourClassInstance) {
        verifications.add(verification);
        lastVerification = verification;
		return verification;
    }

    public void behaviourClassVerificationStarting(Class behaviourClass) {
        startedBehaviourClass = behaviourClass;
    }
    
    public void behaviourClassVerificationEnding(Class behaviourClass) {
        endedBehaviourClass = behaviourClass;
    }
    
    public ResponsibilityVerification verification(int i) {
        return (ResponsibilityVerification) verifications.get(i);
    }
}
/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listeners;

import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.Listener;

/**
 * Null implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class NullListener implements Listener {

    public void behaviourClassVerificationStarting(Class behaviourClass) {
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
    }

    public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClassInstance) {
    }

    public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification verification, Object behaviourClassInstance) {
		return verification;
    }
    
    public String toString() {
        String fullName = getClass().getName();
        String className = fullName.substring(fullName.lastIndexOf('.') + 1);
        return className;
    }
}

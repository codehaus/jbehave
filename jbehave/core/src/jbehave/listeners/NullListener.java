/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listeners;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.Listener;

/**
 * Null implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class NullListener implements Listener {

    public void specVerificationStarting(Class spec) {
    }

    public void specVerificationEnding(Class spec) {
    }

    public void criteriaVerificationStarting(CriteriaVerifier verifier, Object spec) {
    }

    public CriteriaVerification criteriaVerificationEnding(CriteriaVerification verification, Object specInstance) {
		return verification;
    }
    
    public String toString() {
        String fullName = getClass().getName();
        String className = fullName.substring(fullName.lastIndexOf('.') + 1);
        return className;
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listener;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;

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

    public void criteriaVerificationStarting(CriteriaVerifier verifier) {
    }

    public void criteriaVerificationEnding(CriteriaVerification verification) {
    }
    
    public String toString() {
        String fullName = getClass().getName();
        String className = fullName.substring(fullName.lastIndexOf('.') + 1);
        return className;
    }
}

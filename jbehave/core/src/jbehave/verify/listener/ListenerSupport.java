/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.verify.listener;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;
import jbehave.verify.Verifier;

/**
 * Stub implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ListenerSupport implements Listener {

    public void verificationStarted(Verifier verifier) {
    }

    public void verificationEnded(Verifier verifier) {
    }

    public void specVerificationStarted(Class spec) {
    }

    public void specVerificationEnded(Class spec) {
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier verifier) {
    }

    public void afterCriteriaVerificationEnds(CriteriaVerification verification) {
    }
    
    public String toString() {
        String fullName = getClass().getName();
        String className = fullName.substring(fullName.lastIndexOf('.') + 1);
        return className;
    }
}

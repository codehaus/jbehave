/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.Listener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TraceListener implements Listener {
    private class Log {
        public void trace(String message) {
//            System.err.println(message);
        }
    }
    Log log = new Log();

    public void behaviourClassVerificationStarting(Class behaviourClass) {
        log.trace("behaviourClassVerificationStarted: " + behaviourClass.getName());
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
        log.trace("behaviourClassVerificationEnded: " + behaviourClass.getName());
    }

    public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClassInstance) {
        log.trace("responsibilityVerificationStarting: " + verifier.getName());
    }

    public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification evaluation, Object behaviourClassInstance) {
        log.trace("responsibilityVerificationEnding: " + evaluation.toString());
		return evaluation;
    }
}

/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.CriteriaVerifier;
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

    public void specVerificationStarting(Class spec) {
        log.trace("specVerificationStarted: " + spec.getName());
    }

    public void specVerificationEnding(Class spec) {
        log.trace("specVerificationEnded: " + spec.getName());
    }

    public void criteriaVerificationStarting(CriteriaVerifier verifier) {
        log.trace("beforeCriteriaVerificationStarts: " + verifier.getName());
    }

    public CriteriaVerification criteriaVerificationEnding(CriteriaVerification evaluation) {
        log.trace("afterCriteriaVerificationEnds: " + evaluation.toString());
		return evaluation;
    }
}

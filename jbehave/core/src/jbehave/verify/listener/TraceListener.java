/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.verify.listener;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;
import jbehave.verify.Verifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TraceListener implements Listener {
    Log log = LogFactory.getLog(TraceListener.class);

    public void verificationStarted(Verifier verifier) {
        log.trace("verificationStarted");
    }

    public void verificationEnded(Verifier verifier) {
        log.trace("verificationEnded");
    }

    public void specVerificationStarted(Class spec) {
        log.trace("specVerificationStarted: " + spec.getName());
    }

    public void specVerificationEnded(Class spec) {
        log.trace("specVerificationEnded: " + spec.getName());
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier verifier) {
        log.trace("beforeCriteriaVerificationStarts: " + verifier.getName());
    }

    public void afterCriteriaVerificationEnds(CriteriaVerification evaluation) {
        log.trace("afterCriteriaVerificationEnds: " + evaluation.toString());
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.verify.listener;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerificationResult;
import jbehave.verify.Evaluator;

/**
 * Stub implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ListenerSupport implements Listener {

    public void verificationStarted(Evaluator runner) {
    }

    public void verificationEnded(Evaluator runner) {
    }

    public void specVerificationStarted(Class behaviourClass) {
    }

    public void specVerificationEnded(Class behaviourClass) {
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier behaviour) {
    }

    public void afterCriteriaVerificationEnds(CriteriaVerificationResult behaviourResult) {
    }
}

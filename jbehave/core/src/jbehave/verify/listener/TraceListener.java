/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.verify.listener;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerificationResult;
import jbehave.verify.Evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TraceListener implements Listener {
    Log log = LogFactory.getLog(TraceListener.class);

    public void verificationStarted(Evaluator runner) {
        log.trace("runStarted");
    }

    public void verificationEnded(Evaluator runner) {
        log.trace("runEnded");
    }

    public void specVerificationStarted(Class behaviour) {
        log.trace("behaviourEvaluationStarted: " + behaviour.getName());
    }

    public void specVerificationEnded(Class behaviour) {
        log.trace("behaviourEvaluationEnded: " + behaviour.getName());
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier criterion) {
        log.trace("beforeCriterionEvaluationStarts: " + criterion.getName());
    }

    public void afterCriteriaVerificationEnds(CriteriaVerificationResult evaluation) {
        log.trace("afterCriterionEvaluationEnds: " + evaluation.toString());
    }
}

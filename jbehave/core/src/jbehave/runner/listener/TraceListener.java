/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.runner.listener;

import jbehave.framework.Criterion;
import jbehave.framework.CriterionEvaluation;
import jbehave.runner.Evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TraceListener implements Listener {
    Log log = LogFactory.getLog(TraceListener.class);

    public void runStarted(Evaluator runner) {
        log.trace("runStarted");
    }

    public void runEnded(Evaluator runner) {
        log.trace("runEnded");
    }

    public void behaviourEvaluationStarted(Class behaviour) {
        log.trace("behaviourEvaluationStarted: " + behaviour.getName());
    }

    public void behaviourEvaluationEnded(Class behaviour) {
        log.trace("behaviourEvaluationEnded: " + behaviour.getName());
    }

    public void beforeCriterionEvaluationStarts(Criterion criterion) {
        log.trace("beforeCriterionEvaluationStarts: " + criterion.getName());
    }

    public void afterCriterionEvaluationEnds(CriterionEvaluation evaluation) {
        log.trace("afterCriterionEvaluationEnds: " + evaluation.toString());
    }
}

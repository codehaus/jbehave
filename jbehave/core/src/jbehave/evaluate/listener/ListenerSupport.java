/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.evaluate.listener;

import jbehave.evaluate.Evaluator;
import jbehave.framework.Criteria;
import jbehave.framework.CriteriaVerification;

/**
 * Stub implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ListenerSupport implements Listener {

    public void runStarted(Evaluator runner) {
    }

    public void runEnded(Evaluator runner) {
    }

    public void behaviourEvaluationStarted(Class behaviourClass) {
    }

    public void behaviourEvaluationEnded(Class behaviourClass) {
    }

    public void beforeCriterionEvaluationStarts(Criteria behaviour) {
    }

    public void afterCriterionEvaluationEnds(CriteriaVerification behaviourResult) {
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.runner.listener;

import jbehave.framework.Criterion;
import jbehave.framework.Evaluation;
import jbehave.runner.BehaviourRunner;

/**
 * Stub implementations of the listener event methods.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ListenerSupport implements Listener {

    public void runStarted(BehaviourRunner runner) {
    }

    public void runEnded(BehaviourRunner runner) {
    }

    public void behaviourEvaluationStarted(Class behaviourClass) {
    }

    public void behaviourEvaluationEnded(Class behaviourClass) {
    }

    public void beforeCriterionEvaluationStarts(Criterion behaviour) {
    }

    public void afterCriterionEvaluationEnds(Evaluation behaviourResult) {
    }
}

/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.evaluate.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.evaluate.Evaluator;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerificationResult;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * &lt;plug&gt;This would be so much cleaner in Ruby.&lt;/plug&gt;
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CompositeListener implements Listener {
    private final List listeners = new ArrayList();
    
    /**
     * The composition method for the composite.
     */
    public void add(Listener listener) {
        listeners.add(listener);
    }
    
    // Listener methods
    
    public void runStarted(Evaluator runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).runStarted(runner);
        }
    }

    public void runEnded(Evaluator runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).runEnded(runner);
        }
    }

    public void behaviourEvaluationStarted(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourEvaluationStarted(behaviourClass);
        }
    }

    public void behaviourEvaluationEnded(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourEvaluationEnded(behaviourClass);
        }
    }

    public void beforeCriterionEvaluationStarts(CriteriaVerifier behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).beforeCriterionEvaluationStarts(behaviour);
        }
    }

    public void afterCriterionEvaluationEnds(CriteriaVerificationResult behaviourResult) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).afterCriterionEvaluationEnds(behaviourResult);
        }
    }
}

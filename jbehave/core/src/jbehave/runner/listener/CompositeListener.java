/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.runner.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.Criterion;
import jbehave.framework.Evaluation;
import jbehave.runner.SpecificationRunner;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * <plug>This would be so much cleaner in Ruby.</plug>
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
    
    public void runStarted(SpecificationRunner runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).runStarted(runner);
        }
    }

    public void runEnded(SpecificationRunner runner) {
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

    public void beforeCriterionEvaluationStarts(Criterion behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).beforeCriterionEvaluationStarts(behaviour);
        }
    }

    public void afterCriterionEvaluationEnds(Evaluation behaviourResult) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).afterCriterionEvaluationEnds(behaviourResult);
        }
    }
}

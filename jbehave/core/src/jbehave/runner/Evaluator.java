/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.runner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jbehave.framework.Criterion;
import jbehave.framework.CriterionEvaluation;
import jbehave.framework.CriteriaSupport;
import jbehave.runner.listener.CompositeListener;
import jbehave.runner.listener.Listener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Evaluator {
    private final List behaviourClasses = new ArrayList();
    private final Map behaviourMap = new HashMap();
    private final CompositeListener listeners = new CompositeListener();
    private int behaviourCount = 0;

    public void addBehaviourClass(Class behaviourClass) {
        Collection behaviours = CriteriaSupport.getCriteria(behaviourClass);
        behaviourClasses.add(behaviourClass);
        behaviourMap.put(behaviourClass, behaviours);
        behaviourCount += behaviours.size();
    }
    
    public int countBehaviourClasses() {
        return behaviourClasses.size();
    }
    
    public int countBehaviours() {
        return behaviourCount;
    }

    public Class getBehaviourClass(int i) {
        return (Class)behaviourClasses.get(i);
    }
    
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void evaluateCriteria() {
        listeners.runStarted(this);
        for (Iterator i = behaviourClasses.iterator(); i.hasNext();) {
            final Class behaviourClass = (Class)i.next();
            listeners.behaviourEvaluationStarted(behaviourClass);
            
            final Collection criteria = (Collection)behaviourMap.get(behaviourClass);
            for (Iterator j = criteria.iterator(); j.hasNext();) {
                final Criterion behaviour = (Criterion)j.next();
                listeners.beforeCriterionEvaluationStarts(behaviour);
                CriterionEvaluation behaviourResult = behaviour.evaluate();
                listeners.afterCriterionEvaluationEnds(behaviourResult);
            }
            listeners.behaviourEvaluationEnded(behaviourClass);
        }
        listeners.runEnded(this);
    }
}

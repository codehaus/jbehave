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

import jbehave.framework.Behaviour;
import jbehave.framework.BehaviourResult;
import jbehave.framework.BehavioursSupport;
import jbehave.runner.listener.CompositeListener;
import jbehave.runner.listener.Listener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourRunner {
    private final List behaviourClasses = new ArrayList();
    private final Map behaviourMap = new HashMap();
    private final CompositeListener listeners = new CompositeListener();
    private int behaviourCount = 0;

    public void addBehaviourClass(Class behaviourClass) {
        Collection behaviours = BehavioursSupport.getBehaviours(behaviourClass);
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

    public void runBehaviours() {
        listeners.runStarted(this);
        for (Iterator i = behaviourClasses.iterator(); i.hasNext();) {
            final Class behaviourClass = (Class)i.next();
            listeners.behaviourClassStarted(behaviourClass);
            
            final Collection behaviours = (Collection)behaviourMap.get(behaviourClass);
            for (Iterator j = behaviours.iterator(); j.hasNext();) {
                final Behaviour behaviour = (Behaviour)j.next();
                listeners.behaviourStarted(behaviour);
                BehaviourResult behaviourResult = behaviour.run();
                listeners.behaviourEnded(behaviourResult);
            }
            listeners.behaviourClassEnded(behaviourClass);
        }
        listeners.runEnded(this);
    }
}

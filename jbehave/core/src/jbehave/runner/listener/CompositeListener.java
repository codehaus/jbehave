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

import jbehave.framework.Behaviour;
import jbehave.framework.BehaviourResult;
import jbehave.runner.BehaviourRunner;

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
    
    public void runStarted(BehaviourRunner runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).runStarted(runner);
        }
    }

    public void runEnded(BehaviourRunner runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).runEnded(runner);
        }
    }

    public void behaviourClassStarted(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourClassStarted(behaviourClass);
        }
    }

    public void behaviourClassEnded(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourClassEnded(behaviourClass);
        }
    }

    public void behaviourStarted(Behaviour behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourStarted(behaviour);
        }
    }

    public void behaviourEnded(BehaviourResult behaviourResult) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourEnded(behaviourResult);
        }
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.runner.listener;

import jbehave.framework.Behaviour;
import jbehave.framework.BehaviourResult;
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

    public void behaviourClassStarted(Class behaviourClass) {
    }

    public void behaviourClassEnded(Class behaviourClass) {
    }

    public void behaviourStarted(Behaviour behaviour) {
    }

    public void behaviourEnded(BehaviourResult behaviourResult) {
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.core.BehaviourClassContainer;
import jbehave.core.responsibility.BehaviourClassVerifierBehaviour;
import jbehave.core.responsibility.ExecutingResponsibilityVerifierBehaviour;
import jbehave.core.responsibility.ResultBehaviour;
import jbehave.listeners.CompositeListenerBehaviour;
import jbehave.listeners.TextListenerBehaviour;
import jbehave.listeners.TimerBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
            ExecutingResponsibilityVerifierBehaviour.class,
            BehaviourClassVerifierBehaviour.class,
            ResultBehaviour.class,
            TextListenerBehaviour.class,
            TimerBehaviour.class,
			CompositeListenerBehaviour.class
        };
    }
}

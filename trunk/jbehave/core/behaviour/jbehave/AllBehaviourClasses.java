/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.BehaviourClassContainer;
import jbehave.framework.responsibility.BehaviourClassVerifierBehaviour;
import jbehave.framework.responsibility.ExecutingResponsibilityVerifierBehaviour;
import jbehave.framework.responsibility.ResultBehaviour;
import jbehave.listeners.TextListenerBehaviour;
import jbehave.listeners.TimerBehaviour;
import jbehave.listeners.CompositeListenerBehaviour;

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

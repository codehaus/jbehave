/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.ResponsibilityVerificationBehaviour;
import jbehave.framework.ResponsibilityVerifierBehaviour;
import jbehave.framework.BehaviourClassContainer;
import jbehave.framework.BehaviourClassVerifierBehaviour;
import jbehave.listeners.TextListenerBehaviour;
import jbehave.listeners.TimerBehaviour;
import jbehave.listeners.CompositeListenerBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviours implements BehaviourClassContainer {
    public Class[] getResponsibilities() {
        return new Class[] {
            ResponsibilityVerifierBehaviour.class,
            BehaviourClassVerifierBehaviour.class,
            ResponsibilityVerificationBehaviour.class,
            TextListenerBehaviour.class,
            TimerBehaviour.class,
			CompositeListenerBehaviour.class
        };
    }
}

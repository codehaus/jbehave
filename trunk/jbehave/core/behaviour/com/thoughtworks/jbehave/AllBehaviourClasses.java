/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.listeners.BehaviourClassListenersBehaviour;
import com.thoughtworks.jbehave.core.listeners.MethodListenersBehaviour;
import com.thoughtworks.jbehave.core.listeners.TextListenerBehaviour;
import com.thoughtworks.jbehave.core.listeners.TimerBehaviour;
import com.thoughtworks.jbehave.core.verify.BehaviourClassVerifierBehaviour;
import com.thoughtworks.jbehave.core.verify.ExecutingMethodVerifierBehaviour;
import com.thoughtworks.jbehave.core.verify.ResultBehaviour;
import com.thoughtworks.jbehave.util.ConvertCaseBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
            ExecutingMethodVerifierBehaviour.class,
            BehaviourClassVerifierBehaviour.class,
            ResultBehaviour.class,
            TextListenerBehaviour.class,
            TimerBehaviour.class,
			BehaviourClassListenersBehaviour.class,
			MethodListenersBehaviour.class,
            ConvertCaseBehaviour.class
        };
    }
}

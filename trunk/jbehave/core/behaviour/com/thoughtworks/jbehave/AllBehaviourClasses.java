/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave;

import com.thoughtworks.jbehave.core.BehaviourClassBehaviour;
import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.BehaviourMethodBehaviour;
import com.thoughtworks.jbehave.core.BehaviourVerifierBehaviour;
import com.thoughtworks.jbehave.core.ResultBehaviour;
import com.thoughtworks.jbehave.core.invokers.DontInvokeMethodBehaviour;
import com.thoughtworks.jbehave.core.invokers.InvokeMethodWithSetUpAndTearDownBehaviour;
import com.thoughtworks.jbehave.core.listeners.TextListenerBehaviour;
import com.thoughtworks.jbehave.core.listeners.TimerBehaviour;
import com.thoughtworks.jbehave.util.ConvertCaseBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
            BehaviourClassBehaviour.class,
            BehaviourMethodBehaviour.class,
            BehaviourVerifierBehaviour.class,
            ResultBehaviour.class,
            DontInvokeMethodBehaviour.class,
            InvokeMethodWithSetUpAndTearDownBehaviour.class,
            TextListenerBehaviour.class,
            TimerBehaviour.class,
            ConvertCaseBehaviour.class
        };
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.BehaviourClassBehaviour;
import com.thoughtworks.jbehave.core.BehaviourMethodBehaviour;
import com.thoughtworks.jbehave.core.listeners.TextReporterBehaviour;
import com.thoughtworks.jbehave.core.verifiers.DontInvokeMethodBehaviour;
import com.thoughtworks.jbehave.core.verifiers.InvokeMethodWithSetUpAndTearDownBehaviour;
import com.thoughtworks.jbehave.util.ConvertCaseBehaviour;
import com.thoughtworks.jbehave.util.TimerBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                DontInvokeMethodBehaviour.class,
                InvokeMethodWithSetUpAndTearDownBehaviour.class,
                TextReporterBehaviour.class,
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                ConvertCaseBehaviour.class,
                TimerBehaviour.class
        };
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.behaviour.BehaviourClassBehaviour;
import com.thoughtworks.jbehave.core.behaviour.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.behaviour.BehaviourMethodBehaviour;
import com.thoughtworks.jbehave.core.behaviour.BehaviourMethodVerifierBehaviour;
import com.thoughtworks.jbehave.core.invoker.DontInvokeMethodBehaviour;
import com.thoughtworks.jbehave.core.invoker.InvokeMethodWithSetUpAndTearDownBehaviour;
import com.thoughtworks.jbehave.core.listener.PlainTextMethodListenerBehaviour;
import com.thoughtworks.jbehave.core.minimock.ExpectationBehaviour;
import com.thoughtworks.jbehave.core.minimock.MockObjectBehaviour;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMockBehaviour;
import com.thoughtworks.jbehave.core.util.ConvertCaseBehaviour;
import com.thoughtworks.jbehave.core.util.TimerBehaviour;
import com.thoughtworks.jbehave.core.visitor.VisitableArrayListBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                BehaviourMethodVerifierBehaviour.class,
                DontInvokeMethodBehaviour.class,
                InvokeMethodWithSetUpAndTearDownBehaviour.class,
                PlainTextMethodListenerBehaviour.class,
                ExpectationBehaviour.class,
                MockObjectBehaviour.class,
                UsingMiniMockBehaviour.class,
                ConvertCaseBehaviour.class,
                TimerBehaviour.class,
                VisitableArrayListBehaviour.class
        };
    }
}

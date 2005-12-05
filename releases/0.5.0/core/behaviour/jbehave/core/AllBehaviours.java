/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.core;

import jbehave.core.behaviour.BehaviourClassBehaviour;
import jbehave.core.behaviour.BehaviourMethodBehaviour;
import jbehave.core.behaviour.BehaviourMethodVerifierBehaviour;
import jbehave.core.behaviour.Behaviours;
import jbehave.core.listener.PlainTextMethodListenerBehaviour;
import jbehave.core.minimock.ExpectationBehaviour;
import jbehave.core.minimock.MockObjectBehaviour;
import jbehave.core.minimock.UsingMiniMockBehaviour;
import jbehave.core.result.ResultBehaviour;
import jbehave.core.util.ConvertCaseBehaviour;
import jbehave.core.util.TimerBehaviour;
import jbehave.core.visitor.VisitableArrayListBehaviour;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviours implements Behaviours {
    public Class[] getBehaviourClasses() {
        return new Class[] {
        		RunBehaviour.class,
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                BehaviourMethodVerifierBehaviour.class,
                PlainTextMethodListenerBehaviour.class,
                ExpectationBehaviour.class,
                MockObjectBehaviour.class,
                UsingMiniMockBehaviour.class,
                ResultBehaviour.class,
                ConvertCaseBehaviour.class,
                TimerBehaviour.class,
                VisitableArrayListBehaviour.class
        };
    }
}

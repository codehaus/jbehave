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
import jbehave.core.behaviour.BehaviourVerifierBehaviour;
import jbehave.core.behaviour.Behaviours;
import jbehave.core.listener.PlainTextMethodListenerBehaviour;
import jbehave.core.minimock.MiniMockObjectBehaviour;
import jbehave.core.minimock.UsingMiniMockBehaviour;
import jbehave.core.mock.ExpectationBehaviour;
import jbehave.core.result.ResultBehaviour;
import jbehave.core.story.listener.PlainTextListenerBehaviour;


public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                UsingConstraintsBehaviour.class,
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                BehaviourVerifierBehaviour.class,
                PlainTextMethodListenerBehaviour.class,
                MiniMockObjectBehaviour.class,
                UsingMiniMockBehaviour.class,
                ExpectationBehaviour.class,
                ExpectationBehaviour.class,
                ResultBehaviour.class,
                ResultBehaviour.class,
                jbehave.core.story.AllBehaviours.class
        };
    }
}

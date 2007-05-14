/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.core;

import org.jbehave.core.behaviour.BehaviourClassBehaviour;
import org.jbehave.core.behaviour.BehaviourMethodBehaviour;
import org.jbehave.core.behaviour.BehaviourVerifierBehaviour;
import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.exception.JBehaveFrameworkErrorBehaviour;
import org.jbehave.core.listener.PlainTextMethodListenerBehaviour;
import org.jbehave.core.matchers.UsingCollectionMatchersBehaviour;
import org.jbehave.core.matchers.UsingEqualityMatchersBehaviour;
import org.jbehave.core.matchers.UsingExceptionsBehaviour;
import org.jbehave.core.matchers.UsingLogicalMatchersBehaviour;
import org.jbehave.core.matchers.UsingStringMatchersBehaviour;
import org.jbehave.core.minimock.MiniMockObjectBehaviour;
import org.jbehave.core.minimock.UsingMiniMockBehaviour;
import org.jbehave.core.mock.ExpectationBehaviour;
import org.jbehave.core.mock.UsingMatchersBehaviour;
import org.jbehave.core.result.ResultBehaviour;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {UsingCollectionMatchersBehaviour.class,
                UsingEqualityMatchersBehaviour.class,
                UsingLogicalMatchersBehaviour.class,
                UsingExceptionsBehaviour.class,
                UsingMatchersBehaviour.class,
                UsingStringMatchersBehaviour.class,
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                BehaviourVerifierBehaviour.class,
                PlainTextMethodListenerBehaviour.class,
                MiniMockObjectBehaviour.class,
                UsingMiniMockBehaviour.class,
                ExpectationBehaviour.class,
                ResultBehaviour.class,
                org.jbehave.core.story.AllBehaviours.class,
                org.jbehave.core.matchers.AllBehaviours.class,
                JBehaveFrameworkErrorBehaviour.class,
        };
    }
}

/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.core;

import org.jbehave.core.behaviour.BehaviourBehaviours;
import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.exception.ExceptionBehaviours;
import org.jbehave.core.listener.ListenerBehaviours;
import org.jbehave.core.matchers.MatchersBehaviours;
import org.jbehave.core.minimock.MiniMockBehaviours;
import org.jbehave.core.mock.MockBehaviours;
import org.jbehave.core.result.ResultBehaviours;
import org.jbehave.core.story.StoryBehaviours;
import org.jbehave.core.threaded.ThreadedBehaviours;
import org.jbehave.core.util.UtilBehaviours;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                BehaviourRunnerBehaviour.class,
                BehaviourBehaviours.class,
                ExceptionBehaviours.class,
                ListenerBehaviours.class,
                MatchersBehaviours.class,
                MiniMockBehaviours.class,
                MockBehaviours.class,
                ResultBehaviours.class,
                StoryBehaviours.class,
                ThreadedBehaviours.class,
                UtilBehaviours.class
        };
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story;

import com.thoughtworks.jbehave.core.behaviour.BehaviourClassContainer;
import com.thoughtworks.jbehave.extensions.story.codegen.StoryParserBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.NarrativeBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.ScenarioUsingMiniMockBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.StoryBehaviour;
import com.thoughtworks.jbehave.extensions.story.invoker.VisitingScenarioInvokerBehaviour;
import com.thoughtworks.jbehave.extensions.story.listener.PlainTextScenarioListenerBehaviour;
import com.thoughtworks.jbehave.extensions.story.verifier.StoryVerifierBehaviour;
import com.thoughtworks.jbehave.extensions.story.visitor.VisitableUsingMiniMockBehaviour;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviourClasses extends Object implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                StoryParserBehaviour.class,
                NarrativeBehaviour.class,
                ScenarioUsingMiniMockBehaviour.class,
                StoryBehaviour.class,
                VisitingScenarioInvokerBehaviour.class,
                PlainTextScenarioListenerBehaviour.class,
                StoryVerifierBehaviour.class,
                VisitableUsingMiniMockBehaviour.class
        };
    }
}

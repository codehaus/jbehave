/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.extensions.story.base.EventBehaviour;
import com.thoughtworks.jbehave.extensions.story.base.ExpectationBehaviour;
import com.thoughtworks.jbehave.extensions.story.base.GivenBaseBehaviour;
import com.thoughtworks.jbehave.extensions.story.base.StoryBehaviour;
import com.thoughtworks.jbehave.extensions.story.codegen.StoryParserBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.ContextBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.OutcomeBehaviour;
import com.thoughtworks.jbehave.extensions.story.domain.ScenarioBehaviour;
import com.thoughtworks.jbehave.extensions.story.listener.TextScenarioListenerBehaviour;
import com.thoughtworks.jbehave.extensions.story.runner.StoryRunnerBehaviour;
import com.thoughtworks.jbehave.extensions.story.visitor.VisitableArrayListBehaviour;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviourClasses extends Object implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                EventBehaviour.class,
                ExpectationBehaviour.class,
                GivenBaseBehaviour.class,
                StoryBehaviour.class,
                StoryParserBehaviour.class,
                ContextBehaviour.class,
                OutcomeBehaviour.class,
                ScenarioBehaviour.class,
                TextScenarioListenerBehaviour.class,
                StoryRunnerBehaviour.class,
                VisitableArrayListBehaviour.class
        };
    }
}

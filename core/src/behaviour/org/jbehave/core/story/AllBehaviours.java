/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story;

import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.story.codegen.parser.TextStoryParserBehaviour;
import org.jbehave.core.story.domain.AbstractStepBehaviour;
import org.jbehave.core.story.domain.GivenScenarioBehaviour;
import org.jbehave.core.story.domain.GivenStepBehaviour;
import org.jbehave.core.story.domain.MultiStepScenarioBehaviour;
import org.jbehave.core.story.domain.NarrativeBehaviour;
import org.jbehave.core.story.domain.OutcomeStepBehaviour;
import org.jbehave.core.story.domain.ScenarioDrivenStoryBehaviour;
import org.jbehave.core.story.listener.PlainTextScenarioListenerBehaviour;
import org.jbehave.core.story.renderer.PlainTextRendererBehaviour;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                StoryBuilderBehaviour.class,
                StoryRunnerBehaviour.class,
                StoryPrinterBehaviour.class,
                StoryToDirectoryPrinterBehaviour.class,
                TextStoryParserBehaviour.class,
                GivenStepBehaviour.class,
                GivenScenarioBehaviour.class,
                MultiStepScenarioBehaviour.class,
                NarrativeBehaviour.class,
                OutcomeStepBehaviour.class,
                ScenarioDrivenStoryBehaviour.class,
                AbstractStepBehaviour.class,
                PlainTextScenarioListenerBehaviour.class,
				PlainTextRendererBehaviour.class,
        };
    }
}

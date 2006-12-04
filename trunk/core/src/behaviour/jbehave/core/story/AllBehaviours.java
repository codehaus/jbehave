/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story;

import jbehave.core.behaviour.Behaviours;
import jbehave.core.story.codegen.parser.TextStoryParserBehaviour;
import jbehave.core.story.domain.AbstractStepBehaviour;
import jbehave.core.story.domain.GivenScenarioBehaviour;
import jbehave.core.story.domain.GivenStepBehaviour;
import jbehave.core.story.domain.MultiStepScenarioBehaviour;
import jbehave.core.story.domain.NarrativeBehaviour;
import jbehave.core.story.domain.OutcomeStepBehaviour;
import jbehave.core.story.domain.ScenarioDrivenStoryBehaviour;
import jbehave.core.story.listener.PlainTextScenarioListenerBehaviour;
import jbehave.core.story.renderer.PlainTextRendererBehaviour;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                StoryBuilderBehaviour.class,
                StoryRunnerBehaviour.class,
                TextStoryParserBehaviour.class,
//                EventOutcomeStepBehaviour.class,
//                EventsBehaviour.class,
//                GivensBehaviour.class,
                GivenStepBehaviour.class,
                GivenScenarioBehaviour.class,
                MultiStepScenarioBehaviour.class,
                NarrativeBehaviour.class,
//                OutcomesBehaviour.class,
                OutcomeStepBehaviour.class,
                ScenarioDrivenStoryBehaviour.class,
//                ScenariosBehaviour.class,
//                ScenarioUsingMiniMockBehaviour.class,
                AbstractStepBehaviour.class,
//                StepsBehaviour.class,
                PlainTextScenarioListenerBehaviour.class,
				PlainTextRendererBehaviour.class,
        };
    }
}

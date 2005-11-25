/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.story;

import jbehave.core.behaviour.Behaviours;
import jbehave.story.codegen.parser.TextStoryParserBehaviour;
import jbehave.story.domain.EventsBehaviour;
import jbehave.story.domain.GivenScenarioBehaviour;
import jbehave.story.domain.GivensBehaviour;
import jbehave.story.domain.NarrativeBehaviour;
import jbehave.story.domain.OutcomesBehaviour;
import jbehave.story.domain.ScenarioDrivenStoryBehaviour;
import jbehave.story.domain.ScenarioUsingMiniMockBehaviour;
import jbehave.story.invoker.VisitingScenarioInvokerBehaviour;
import jbehave.story.listener.PlainTextScenarioListenerBehaviour;
import jbehave.story.renderer.PlainTextRendererBehaviour;
import jbehave.story.verifier.StoryVerifierBehaviour;
import jbehave.story.verifier.VisitingScenarioVerifierBehaviour;
import jbehave.story.visitor.VisitableArrayListBehaviour;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours extends Object implements Behaviours {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                TextStoryParserBehaviour.class,
				GivenScenarioBehaviour.class,
                NarrativeBehaviour.class,
                OutcomesBehaviour.class,
                GivensBehaviour.class,
                EventsBehaviour.class,
                ScenarioUsingMiniMockBehaviour.class,
                ScenarioDrivenStoryBehaviour.class,
                VisitingScenarioInvokerBehaviour.class,
                PlainTextScenarioListenerBehaviour.class,
				PlainTextRendererBehaviour.class,
                StoryVerifierBehaviour.class,
                VisitableArrayListBehaviour.class,
                VisitingScenarioVerifierBehaviour.class,
        };
    }
}

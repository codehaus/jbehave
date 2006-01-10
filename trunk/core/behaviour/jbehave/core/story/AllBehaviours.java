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
import jbehave.core.story.domain.AcceptanceCriteriaBehaviour;
import jbehave.core.story.domain.EventsBehaviour;
import jbehave.core.story.domain.GivenScenarioBehaviour;
import jbehave.core.story.domain.GivensBehaviour;
import jbehave.core.story.domain.NarrativeBehaviour;
import jbehave.core.story.domain.OutcomesBehaviour;
import jbehave.core.story.domain.ScenarioDrivenStoryBehaviour;
import jbehave.core.story.domain.ScenarioUsingMiniMockBehaviour;
import jbehave.core.story.invoker.VisitingScenarioInvokerBehaviour;
import jbehave.core.story.listener.PlainTextScenarioListenerBehaviour;
import jbehave.core.story.renderer.PlainTextRendererBehaviour;
import jbehave.core.story.verifier.StoryVerifierBehaviour;
import jbehave.core.story.verifier.VisitingScenarioVerifierBehaviour;
import jbehave.core.story.visitor.VisitableArrayListBehaviour;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
        		RunBehaviour.class,
        		AcceptanceCriteriaBehaviour.class,
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

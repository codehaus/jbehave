/*
 * Created on 21-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Scenarios;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.core.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStoryBehaviour extends UsingMiniMock {
    public void shouldPassItselfAndItsComponentsToVisitorInCorrectOrder() throws Exception {
        // given...
        Narrative narrative = new Narrative("","","");
        Scenarios scenarios = new Scenarios();
        ScenarioDrivenStory story = new ScenarioDrivenStory(narrative, scenarios);
        Mock invoker = mock(Visitor.class);
        Mock verifier = mock(Visitor.class);

        // expect...
        invoker.expects("visitNarrative").with(narrative);
        verifier.expects("visitNarrative").with(narrative).after(invoker, "visitNarrative");
        invoker.expects("visitScenarios").with(scenarios).after(verifier, "visitNarrative");
        verifier.expects("visitScenarios").with(scenarios).after(invoker, "visitScenarios");
        
        
        // when...
        story.run((Visitor)invoker, (Visitor)verifier);
        
        // then...
        verifyMocks();
    }
}

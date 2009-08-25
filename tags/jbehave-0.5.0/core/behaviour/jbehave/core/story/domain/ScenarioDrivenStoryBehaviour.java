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
import jbehave.core.story.domain.AcceptanceCriteria;
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
        AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
        ScenarioDrivenStory story = new ScenarioDrivenStory(narrative, acceptanceCriteria);
        Mock visitorMock = mock(Visitor.class);

        // expect...
        visitorMock.expects("visitStory").with(story);
        visitorMock.expects("visitNarrative").with(narrative).after("visitStory");
        visitorMock.expects("visitAcceptanceCriteria").with(acceptanceCriteria).after("visitNarrative");
        
        // when...
        story.accept((Visitor)visitorMock);
    }
}

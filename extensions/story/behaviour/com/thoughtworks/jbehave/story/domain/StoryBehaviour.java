/*
 * Created on 21-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBehaviour extends UsingMiniMock {
    public void shouldPassItselfAndItsComponentsToVisitorInCorrectOrder() throws Exception {
        // given...
        Narrative narrative = new Narrative("","","");
        AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
        Story story = new Story(narrative, acceptanceCriteria);
        Mock visitorMock = mock(Visitor.class);

        // expect...
        visitorMock.expects("visitStory").with(story);
        visitorMock.expects("visitNarrative").with(narrative).after("visitStory");
        visitorMock.expects("visitAcceptanceCriteria").with(acceptanceCriteria).after("visitNarrative");
        
        // when...
        story.accept((Visitor)visitorMock);
        
        // verify...
        verifyMocks();
    }
}

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
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Story;

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
        visitorMock.expects("visit").with(story).id("1");
        visitorMock.expects("visit").with(narrative).after("1").id("2");
        visitorMock.expects("visit").with(acceptanceCriteria).after("2");
        
        // when...
        story.accept((Visitor)visitorMock);
        
        // verify...
        verifyMocks();
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBehaviour extends UsingMiniMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Story story = new Story(new Narrative("role", "feature", "benefit"), new AcceptanceCriteria());
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expectsOnce("visitStory").with(story);
        visitor.ignores("visitNarrative").with(instanceOf(Narrative.class));
        
        // when...
        story.accept((Visitor) visitor.proxy());
        
        // verify...
        verifyMocks();
    }
    
    public void shouldPassComponentsToVisitorInCorrectOrder() throws Exception {

        // given...
        AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
        Narrative narrative = new Narrative("role", "feature", "benefit");
        Mock visitor = new Mock(Visitor.class);
        Story story = new Story(narrative, acceptanceCriteria);

        // expect...
        visitor.expectsOnce("visitStory").with(story);
        visitor.expectsOnce("visitNarrative").with(narrative);
        
        // when...
        story.accept((Visitor) visitor.proxy());
        
        // verifyd...
        verifyMocks();
    }
}

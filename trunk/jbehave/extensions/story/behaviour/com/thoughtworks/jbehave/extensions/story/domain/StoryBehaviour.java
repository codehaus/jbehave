/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Story story = new Story(new Narrative("role", "feature", "benefit"), new AcceptanceCriteria());
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitStory").with(same(story));
        visitor.setDefaultStub(new DefaultResultStub());
        
        // when...
        story.accept((Visitor) visitor.proxy());
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellComponentsToAcceptVisitorInCorrectOrder() throws Exception {
        // given...
        Mock acceptanceCriteria = new Mock(AcceptanceCriteria.class, "criteria");
        Mock narrative = new Mock(MockableNarrative.class, "narrative");
        Visitor visitor = (Visitor)stub(Visitor.class);
        Story story = new Story((Narrative)narrative.proxy(), (AcceptanceCriteria)acceptanceCriteria.proxy());

        // expect...
        narrative.expects(once()).method("accept").with(same(visitor));
        acceptanceCriteria.expects(once()).method("accept").with(same(visitor)).after(narrative, "accept");
        
        // when...
        story.accept(visitor);
        
        // then...
        verifyMocks();
    }
}

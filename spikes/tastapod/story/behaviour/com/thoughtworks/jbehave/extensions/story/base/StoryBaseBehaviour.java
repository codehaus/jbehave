/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import com.thoughtworks.jbehave.core.responsibility.Verify;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.base.StoryBase;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.util.Visitable;
import com.thoughtworks.jbehave.extensions.story.util.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBaseBehaviour implements UsingJMock {
    public void shouldBeVisitable() throws Exception {
        Verify.that(Visitable.class.isAssignableFrom(StoryBase.class));
    }
    
    public void shouldPassItselfIntoVisitor() throws Exception {
        // setup
        StoryBase story = new StoryBase("role", "feature", "benefit");
        Mock visitor = new Mock(Visitor.class);
        
        // expect
        visitor.expects(Invoked.once()).method("visitStory").with(Is.equal(story));

        // execute
        story.accept((Visitor) visitor.proxy());
        
        // verify - done by mock
    }
    
    public void shouldTellVisitableScenariosToAcceptVisitor() throws Exception {
        // given...
        StoryBase story = new StoryBase("role", "feature", "benefit");
        Mock scenario1 = new Mock(Scenario.class, "scenario1");
        Mock scenario2 = new Mock(Scenario.class, "scenario2");
        Mock visitor = new Mock(Visitor.class);

        scenario1.expectsOnce("accept", visitor.proxy());
        scenario2.expectsOnce("accept", visitor.proxy());
        visitor.stubs("visitScenario");
        
        // when...
        story.addScenario((Scenario) scenario1.proxy());
        story.addScenario((Scenario) scenario2.proxy());
        story.accept((Visitor) visitor.proxy());
        
        // then... verified by framework
    }
}

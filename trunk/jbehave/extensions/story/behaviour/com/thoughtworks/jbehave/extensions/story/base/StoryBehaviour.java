/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Story story = new Story("role", "feature", "benefit");
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitStory").with(same(story));
        visitor.setDefaultStub(new DefaultResultStub());
        
        // when...
        story.accept((Visitor) visitor.proxy());
    }
    
    public void shouldTellScenariosToAcceptVisitor() throws Exception {
        // given...
        Story story = new Story("role", "feature", "benefit");
        Mock scenario1 = new Mock(MockableScenario.class, "scenario1");
        Mock scenario2 = new Mock(MockableScenario.class, "scenario2");
        Visitor visitor = (Visitor) stub(Visitor.class);

        // expect...
        scenario1.expects(once()).method("accept").with(same(visitor));
        scenario2.expects(once()).method("accept").with(same(visitor));
        
        // when...
        story.addScenario((Scenario) scenario1.proxy());
        story.addScenario((Scenario) scenario2.proxy());
        story.accept(visitor);
    }
}

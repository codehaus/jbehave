/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class EventBehaviour extends UsingJMock {

    private static class SomeEvent extends Event {
        public void occurIn(Environment environment) throws Exception {
        }
    }

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Event event = new SomeEvent();
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitEvent").with(same(event));

        // when...
        event.accept((Visitor)visitor.proxy());
    }
}

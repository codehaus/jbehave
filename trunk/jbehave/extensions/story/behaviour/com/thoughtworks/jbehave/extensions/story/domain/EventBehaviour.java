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
public class EventBehaviour extends UsingMiniMock {

    private static class SomeEvent extends Event {
        public void occurIn(Environment environment) throws Exception {
        }
    }

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Event event = new SomeEvent();
        Mock visitor = mock(Visitor.class);
        
        // expect...
        visitor.expects("visit").with(same(event));

        // when...
        event.accept((Visitor)visitor);
    }
}

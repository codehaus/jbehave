/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleContextBehaviour implements UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable context = new SimpleContext(new Given[0]);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visitContext", context);

        // when...
        context.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
    
    public void shouldTellGivensToAcceptVisitor() throws Exception {
        // given...
        Mock given1 = new Mock(Given.class, "given1");
        Mock given2 = new Mock(Given.class, "given2");
        Visitor visitor = Visitor.NULL;
        
        SimpleContext context = new SimpleContext(
                new Given[] {
                        (Given) given1.proxy(),
                        (Given) given2.proxy()
                }
        );
        
        given1.expectsOnce("accept", visitor);
        given2.expectsOnce("accept", visitor);
        
        // when...
        context.accept(visitor);
        
        // then... verified by framework
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Arrays;
import java.util.Collections;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.util.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleContextBehaviour implements UsingJMock {
    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Context context = new SimpleContext(Collections.EMPTY_LIST);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visit", context);

        // when...
        context.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
    
    public void shouldTellGivensToAcceptVisitor() throws Exception {
        // given...
        Mock given1 = new Mock(Given.class, "given1");
        Mock given2 = new Mock(Given.class, "given2");
        Mock visitor = new Mock(Visitor.class);
        
        Context context = new SimpleContext(
                Arrays.asList(new Given[] {
                        (Given) given1.proxy(),
                        (Given) given2.proxy()}
                ));
        
        visitor.stubs("visit");
        given1.expectsOnce("accept", visitor.proxy());
        given2.expectsOnce("accept", visitor.proxy());

        // when...
        context.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
}

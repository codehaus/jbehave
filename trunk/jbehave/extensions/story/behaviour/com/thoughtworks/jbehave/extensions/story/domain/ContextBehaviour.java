/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ContextBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable context = new Context(new Given[0]);
        Mock visitor = new Mock(Visitor.class);
        visitor.expects(once()).method("visit").with(same(context));

        // when...
        context.accept((Visitor)visitor.proxy());
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellGivensToAcceptVisitorInOrder() throws Exception {
        // given...
        Mock given1 = new Mock(Given.class, "given1");
        Mock given2 = new Mock(Given.class, "given2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        Context context = new Context(
                new Given[] {
                        (Given) given1.proxy(),
                        (Given) given2.proxy()
                }
        );
        
        given1.expects(once()).method("accept").with(same(visitor));
        given2.expects(once()).method("accept").with(same(visitor)).after(given1, "accept");
        
        // when...
        context.accept(visitor);
        
        // then...
        verifyMocks();
    }
    
    private static class SomeRuntimeException extends RuntimeException {
    }
    
    public void shouldPropagateExceptionFromCallToGivensAcceptMethod() throws Exception {
        // given...
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Mock given = new Mock(Given.class);
        Context context = new Context((Given)given.proxy());

        // expect...
        given.expects(atLeastOnce()).method("accept").will(throwException(new SomeRuntimeException()));
        
        // when...
        try {
            context.accept(visitorStub);
            Verify.impossible("Should have propagated SomeRuntimeException");
        } catch (SomeRuntimeException expected) {
        }
        
        // then...
        verifyMocks();
    }
}

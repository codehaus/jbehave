/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.verify.Verify;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleOutcomeBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // expectation...
        Visitable outcome = new SimpleOutcome(new Expectation[0]);
        Mock visitor = new Mock(Visitor.class);
        visitor.expects(once()).method("visitOutcome").with(same(outcome));

        // when...
        outcome.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
    
    public void shouldTellExpectationsToAcceptVisitorInOrder() throws Exception {
        // expectation...
        Mock expectation1 = new Mock(Expectation.class, "expectation1");
        Mock expectation2 = new Mock(Expectation.class, "expectation2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        SimpleOutcome outcome = new SimpleOutcome(
                new Expectation[] {
                        (Expectation) expectation1.proxy(),
                        (Expectation) expectation2.proxy()
                }
        );
        
        expectation1.expects(once()).method("accept").with(same(visitor));
        expectation2.expects(once()).method("accept").with(same(visitor)).after(expectation1, "accept");
        
        // when...
        outcome.accept(visitor);
        
        // then... verified by framework
    }
    
    private static class SomeCheckedException extends Exception {
    }
    
    public void shouldPropagateExceptionFromCallToExpectationsAcceptMethod() throws Exception {
        // given...
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Mock expectation = new Mock(Expectation.class);
        SimpleOutcome outcome = new SimpleOutcome((Expectation)expectation.proxy());

        // expect...
        expectation.expects(atLeastOnce()).method("accept").will(throwException(new SomeCheckedException()));
        
        // when...
        try {
            outcome.accept(visitorStub);
            Verify.impossible("Should have propagated SomeCheckedException");
        } catch (SomeCheckedException expected) {
        }
    }
}

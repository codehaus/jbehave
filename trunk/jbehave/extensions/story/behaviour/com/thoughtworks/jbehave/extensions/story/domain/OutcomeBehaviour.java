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
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class OutcomeBehaviour extends UsingMiniMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // expectation...
        Visitable outcome = new Outcome(new Expectation[0]);
        Mock visitor = mock(Visitor.class);
        visitor.expects("visit").with(same(outcome));

        // when...
        outcome.accept((Visitor)visitor);
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellExpectationsToAcceptVisitorInCorrectOrder() throws Exception {
        // expectation...
        Mock expectation1 = mock(Expectation.class, "expectation1");
        Mock expectation2 = mock(Expectation.class, "expectation2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        Outcome outcome = new Outcome(
                new Expectation[] {
                        (Expectation) expectation1,
                        (Expectation) expectation2
                }
        );
        
        expectation1.expects("accept").with(same(visitor));
        expectation2.expects("accept").with(same(visitor)).after(expectation1, "accept");
        
        // when...
        outcome.accept(visitor);
        
        // then...
        verifyMocks();
    }
    
    private static class SomeRuntimeException extends RuntimeException {
    }
    
    public void shouldPropagateExceptionFromCallToExpectationsAcceptMethod() throws Exception {
        // given...
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Mock expectation = mock(Expectation.class);
        Outcome outcome = new Outcome((Expectation)expectation);

        // expect...
        expectation.expects("accept").atLeastOnce().willThrow(new SomeRuntimeException());
        
        // when...
        try {
            outcome.accept(visitorStub);
            Verify.impossible("Should have propagated SomeRuntimeException");
        } catch (SomeRuntimeException expected) {
        }
        
        // then...
        verifyMocks();
    }
}

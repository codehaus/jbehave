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
public class SimpleOutcomeBehaviour implements UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // expectation...
        Visitable outcome = new SimpleOutcome(new Expectation[0]);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visitOutcome", outcome);

        // when...
        outcome.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
    
    public void shouldTellExpectationsToAcceptVisitor() throws Exception {
        // expectation...
        Mock expectation1 = new Mock(Expectation.class, "expectation1");
        Mock expectation2 = new Mock(Expectation.class, "expectation2");
        Visitor visitor = Visitor.NULL;
        
        SimpleOutcome outcome = new SimpleOutcome(
                new Expectation[] {
                        (Expectation) expectation1.proxy(),
                        (Expectation) expectation2.proxy()
                }
        );
        
        expectation1.expectsOnce("accept", visitor);
        expectation2.expectsOnce("accept", visitor);
        
        // when...
        outcome.accept(visitor);
        
        // then... verified by framework
    }
}

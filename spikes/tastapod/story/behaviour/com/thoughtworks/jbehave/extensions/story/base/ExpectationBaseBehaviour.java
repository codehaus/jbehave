/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ExpectationBaseBehaviour extends UsingJMock {
    public void shouldDispatchItselfIntoCorrectVisitorMethodEachTimeAcceptIsCalled() throws Exception {
        // given...
        Expectation expectation = new ExpectationBase() {
            public void setExpectation(Environment environment) throws Exception {}
            public void verify(Environment environment) throws Exception {}
        };
        
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expectsOnce("visitExpectationBeforeEvent", expectation);
        visitor.expectsOnce("visitExpectationAfterEvent", expectation).after("visitExpectationBeforeEvent");

        // when...
        expectation.accept((Visitor)visitor.proxy());
        expectation.accept((Visitor)visitor.proxy());
    }
}

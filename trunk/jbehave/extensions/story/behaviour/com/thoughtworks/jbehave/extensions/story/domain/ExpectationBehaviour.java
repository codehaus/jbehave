/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ExpectationBehaviour extends UsingJMock {
    
    public void shouldPassItselfIntoCorrectVisitorMethodEachTimeAcceptIsCalled() throws Exception {
        // given...
        Expectation expectation = new Expectation() {
            public void setExpectationIn(Environment environment) throws Exception {}
            public void verify(Environment environment) throws Exception {}
        };
        
        Mock visitor = new Mock(Visitor.class);
        
        visitor.expects(once()).method("visitExpectationBeforeTheEvent").with(same(expectation));
        visitor.expects(once()).method("visitExpectationAfterTheEvent").with(same(expectation)).after("visitExpectationBeforeTheEvent");

        // when...
        expectation.accept((Visitor)visitor.proxy());
        expectation.accept((Visitor)visitor.proxy());
    }
}

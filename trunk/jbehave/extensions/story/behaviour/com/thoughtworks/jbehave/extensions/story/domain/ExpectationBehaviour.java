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
public class ExpectationBehaviour extends UsingMiniMock {
    
    public void shouldPassItselfIntoCorrectVisitorMethodEachTimeAcceptIsCalled() throws Exception {
        // given...
        Expectation expectation = new Expectation() {
            public void setExpectationIn(Environment environment) throws Exception {}
            public void verify(Environment environment) throws Exception {}
        };
        
        Mock visitor = mock(Visitor.class);
        
        visitor.expects("visit").times(2).with(same(expectation));

        // when...
        expectation.accept((Visitor)visitor);
    }
}

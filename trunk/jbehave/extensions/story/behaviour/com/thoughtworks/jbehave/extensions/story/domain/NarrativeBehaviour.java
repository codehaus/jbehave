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
public class NarrativeBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Narrative narrative = new Narrative("role", "feature", "benefit");
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitNarrative").with(same(narrative));

        // when...
        narrative.accept((Visitor)visitor.proxy());
        
        // then..
        verifyMocks();
    }
}

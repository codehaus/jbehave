/*
 * Created on 30-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * This class extends {@link UsingMiniMock} and implements {@Visitable}.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable visitable = new VisitableUsingMiniMock();
        Mock visitor = mock(Visitor.class);

        // expect...
        visitor.expects("visit").with(visitable);
        
        // when...
        visitable.accept((Visitor)visitor);
        
        // then...
        verifyMocks();
    }
}

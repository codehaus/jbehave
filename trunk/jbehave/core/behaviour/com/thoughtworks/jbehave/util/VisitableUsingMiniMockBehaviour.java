/*
 * Created on 30-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

/**
 * This class extends {@link UsingMiniMock} and implements {@Visitable}.
 * 
 * See the JBehave Story Runner extension for some examples of this class in action.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableUsingMiniMockBehaviour extends VisitableUsingMiniMock {
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

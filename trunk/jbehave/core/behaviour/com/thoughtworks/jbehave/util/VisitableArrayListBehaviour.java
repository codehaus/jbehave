/*
 * Created on 29-Aug-2004
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
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableArrayListBehaviour extends UsingMiniMock {
    
    public void shouldTellEachElementToAcceptVisitor() throws Exception {
        // setup...
        Mock child1 = mock(Visitable.class, "child1");
        Mock child2 = mock(Visitable.class, "child2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        VisitableArrayList list = new VisitableArrayList();
        list.add(child1);
        list.add(child2);
        
        // expect...
        child1.expects("accept").with(visitor);
        child2.expects("accept").with(visitor).after(child1, "accept");

        // when...
        list.accept(visitor);
        
        // then...
        verifyMocks();
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableArrayListBehaviour extends UsingJMock {
    
    public void shouldTellEachElementToAcceptVisitor() throws Exception {
        // child...
        Mock child1 = new Mock(Visitable.class, "child1");
        Mock child2 = new Mock(Visitable.class, "child2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        VisitableArrayList list = new VisitableArrayList();
        list.add(child1.proxy());
        list.add(child2.proxy());
        
        child1.expects(once()).method("accept").with(same(visitor));
        child2.expects(once()).method("accept").with(same(visitor));

        // when...
        list.accept(visitor);
        
        // then...
        verifyMocks();
    }
}

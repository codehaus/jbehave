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
public class VisitableArrayListBehaviour implements UsingJMock {
    
    public void shouldTellEachElementToAcceptVisitor() throws Exception {
        // child...
        Mock child1 = new Mock(Visitable.class, "child1");
        Mock child2 = new Mock(Visitable.class, "child2");
        Visitor visitor = Visitor.NULL;
        
        VisitableArrayList list = new VisitableArrayList();
        list.add(child1.proxy());
        list.add(child2.proxy());
        
        child1.expectsOnce("accept", visitor);
        child2.expectsOnce("accept", visitor);

        // when...
        list.accept(visitor);
        
        // then... verified by pixies
    }
}

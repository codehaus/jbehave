/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class CompositeVisitableBehaviour implements UsingJMock {
    protected abstract Visitable getComposite(List visitables);
    
    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable composite = getComposite(Collections.EMPTY_LIST);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visit", composite);

        // when...
        composite.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
    
    public void shouldTellGivensToAcceptVisitor() throws Exception {
        // given...
        Mock child1 = new Mock(Visitable.class, "given1");
        Mock child2 = new Mock(Visitable.class, "given2");
        Mock visitor = new Mock(Visitor.class);
        
        List visitables = new ArrayList();
        visitables.add(child1.proxy());
        visitables.add(child2.proxy());
        Visitable composite = getComposite(visitables);
        
        visitor.stubs("visit");
        child1.expectsOnce("accept", visitor.proxy());
        child2.expectsOnce("accept", visitor.proxy());

        // when...
        composite.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.ArrayList;
import java.util.Collections;

import com.thoughtworks.jbehave.extensions.story.visitor.CompositeVisitable;
import com.thoughtworks.jbehave.extensions.story.visitor.VisitableArrayListBehaviour;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleContextBehaviour extends VisitableArrayListBehaviour {

    protected CompositeVisitable getComposite() {
        return new SimpleContext(new ArrayList());
    }

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable context = new SimpleContext(Collections.EMPTY_LIST);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visitContext", context);

        // when...
        context.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Collections;
import java.util.List;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleOutcomeBehaviour implements UsingJMock {

    protected Visitable getComposite(List visitables) {
        return new SimpleOutcome(visitables);
    }

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Visitable outcome = new SimpleOutcome(Collections.EMPTY_LIST);
        Mock visitor = new Mock(Visitor.class);
        visitor.expectsOnce("visitOutcome", outcome);

        // when...
        outcome.accept((Visitor)visitor.proxy());
        
        // then... verified by pixies
    }
}

/*
 * Created on 30-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableUsingMiniMock extends UsingMiniMock implements Visitable {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

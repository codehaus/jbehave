/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class Given extends UsingJMock implements Visitable {

    public abstract void setUp(Environment environment) throws Exception;
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

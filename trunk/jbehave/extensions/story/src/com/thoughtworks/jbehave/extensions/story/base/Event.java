/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class Event implements Visitable {
    public static Event NULL = new Event() {
        public void occurIn(Environment environment) throws Exception {
        }
    };
    
    public abstract void occurIn(Environment environment) throws Exception;

    public void accept(Visitor visitor) throws Exception {
        visitor.visitEvent(this);
    }
}

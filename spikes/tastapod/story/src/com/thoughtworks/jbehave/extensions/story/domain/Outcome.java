/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Collections;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Outcome extends Visitable {
    public static final Outcome NULL = new Outcome() {
        public List getExpectations() {
            return Collections.EMPTY_LIST;
        }
        public void accept(Visitor visitor) {
        }
    };

    List getExpectations();
}
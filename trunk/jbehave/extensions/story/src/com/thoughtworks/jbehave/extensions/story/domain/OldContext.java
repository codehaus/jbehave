/*
 * Created on 01-Sep-2004
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
 * @deprecated use Context instead
 */
public interface OldContext extends Visitable {
    OldContext NULL = new OldContext() {
        public List getGivens() {
            return Collections.EMPTY_LIST;
        }
        public void accept(Visitor visitor) throws Exception {
        }
    };
}

/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.verify.Verify;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class GivenBehaviour {
    public void shouldBeVisitable() throws Exception {
        Verify.that(Visitable.class.isAssignableFrom(Given.class));
    }
}

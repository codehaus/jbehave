/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.minimock.UsingMocks;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Expectation extends Visitable, UsingMocks {
    void setExpectationIn(Environment environment);
    void verify(Environment environment);
}
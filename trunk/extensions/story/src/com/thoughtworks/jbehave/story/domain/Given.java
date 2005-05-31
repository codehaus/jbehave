/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.UsingMocks;
import com.thoughtworks.jbehave.story.visitor.Visitable;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Given extends Visitable, UsingMocks {
    void setUp(World world) throws Exception;
}
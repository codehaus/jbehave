/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @deprecated use Given abstract class
 */
public interface OldGiven extends Visitable {
    void setUp(Environment environment) throws Exception;
}

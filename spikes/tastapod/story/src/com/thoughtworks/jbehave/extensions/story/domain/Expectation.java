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
 */
public interface Expectation extends Visitable {
    /**
     * Set expectation
     * 
     * Used to set expectations in any mocks before the event
     */
    void setExpectation(Environment environment) throws Exception;
    
    /**
     * Verify expectation after the event
     * 
     * Note: for mocks this will be done automagically
     */
    void verify(Environment environment) throws Exception;
}

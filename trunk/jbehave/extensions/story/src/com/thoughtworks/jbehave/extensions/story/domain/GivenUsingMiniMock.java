/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.minimock.VisitableUsingMiniMock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class GivenUsingMiniMock extends VisitableUsingMiniMock implements Given {
    public abstract void setUp(Environment environment) throws Exception;
}

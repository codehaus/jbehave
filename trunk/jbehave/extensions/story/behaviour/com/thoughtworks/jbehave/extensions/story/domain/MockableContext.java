/*
 * Created on 25-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.base.Given;


public class MockableContext extends Context {
    public MockableContext() {
        super(new Given[0]);
    }
}
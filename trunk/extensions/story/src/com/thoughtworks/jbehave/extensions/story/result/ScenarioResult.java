/*
 * Created on 13-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.result;

import com.thoughtworks.jbehave.core.result.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioResult extends Result {
    public static final Type USES_MOCKS = newType("Uses Mocks", "M");
    
    public ScenarioResult(String name, Throwable cause) {
        super(name, cause);
    }
    
    public ScenarioResult(String name, Type status) {
        super(name, status);
    }

    public boolean succeededUsingMocks() {
        return status() == USES_MOCKS;
    }
}

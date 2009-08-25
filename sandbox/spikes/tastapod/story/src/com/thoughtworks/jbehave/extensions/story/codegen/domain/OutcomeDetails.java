/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class OutcomeDetails {
    private final List expectations = new ArrayList();
    
    public void addExpectation(BasicDetails expectation) {
        expectations.add(expectation);
    }
    
    public List getExpectations() {
        return expectations;
    }
}

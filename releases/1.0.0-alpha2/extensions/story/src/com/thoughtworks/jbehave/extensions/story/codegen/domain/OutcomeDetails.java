/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.domain;

import java.util.ArrayList;
import java.util.Iterator;
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
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator iter = expectations.iterator(); iter.hasNext();) {
            buffer.append(iter.next().toString());
            buffer.append("\n");
        }
        return buffer.toString();
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof OutcomeDetails)) return false;
        
        OutcomeDetails that = (OutcomeDetails)obj;
        return this.expectations.equals(that.expectations);
    }
}

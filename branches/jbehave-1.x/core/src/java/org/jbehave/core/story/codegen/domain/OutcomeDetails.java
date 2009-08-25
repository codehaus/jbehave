/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.codegen.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class OutcomeDetails {
    public final List outcomes = new ArrayList();
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        OutcomeDetails that = (OutcomeDetails)obj;
        return this.outcomes.equals(that.outcomes);
    }

    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + outcomes.hashCode();
        return hashCode;
    }    

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(outcomes);
        return buffer.toString();
    }
        
}

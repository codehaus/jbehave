/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.codegen.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ContextDetails {
    public final List givens = new ArrayList();
	
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
        ContextDetails that = (ContextDetails)obj;
        return this.givens.equals(that.givens);
    }
    
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + givens.hashCode();
        return hashCode;
    }    

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(givens);
        return buffer.toString();
    }
}

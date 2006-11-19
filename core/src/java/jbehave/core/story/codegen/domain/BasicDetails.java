/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.codegen.domain;

import jbehave.core.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BasicDetails {
    public String name = "";

    public String getClassName() {
        return new ConvertCase(name).toCamelCase();
    }
    
    public boolean equals(Object obj)  {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BasicDetails that = (BasicDetails) obj;
        return this.name.equals(that.name);
    }
    
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        return hashCode;
    }    
    
    public String toString() {
        return name;
    }
}

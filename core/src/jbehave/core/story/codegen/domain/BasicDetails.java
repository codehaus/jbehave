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
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof BasicDetails)) return false;
        BasicDetails that = (BasicDetails) obj;
        return this.name.equals(that.name);
    }
    
    public String toString() {
        return name + "\n";
    }
}

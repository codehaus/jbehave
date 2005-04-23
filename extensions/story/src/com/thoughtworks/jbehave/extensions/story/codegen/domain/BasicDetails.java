/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.domain;

import com.thoughtworks.jbehave.core.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BasicDetails {
    private final String name;
    private final String description;

    public BasicDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String description() {
        return description;
    }
    public String getName() {
        return name;
    }
    public String getClassName() {
        return new ConvertCase(name).toCamelCase();
    }
    
    public boolean equals(Object obj)  {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof BasicDetails)) return false;
        BasicDetails that = (BasicDetails) obj;
        return this.name.equals(that.name) && this.description.equals(that.description);
    }
    
    public String toString() {
        return name + ", " + description + "\n";
    }
}

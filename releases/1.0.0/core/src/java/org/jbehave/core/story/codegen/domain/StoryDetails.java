/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.codegen.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class StoryDetails extends BasicDetails {
    public String role = "";
    public String feature = "";
    public String benefit = "";
    public final List scenarios = new ArrayList();
	
	public StoryDetails() {
	}
	
    public StoryDetails(String name, String role, String feature, String benefit) {
		this.name = name;
        this.role = role;
        this.feature = feature;
        this.benefit = benefit;
    }

    public void addScenario(ScenarioDetails scenario) {
        scenarios.add(scenario);
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)){
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        StoryDetails that = (StoryDetails) obj;
        return super.equals(obj)
			&& this.role.equals(that.role)
	        && this.feature.equals(that.feature)
	        && this.benefit.equals(that.benefit)
	        && this.scenarios.equals(that.scenarios);
    }
    
    /**
     * Override hashCode because we implemented {@link #equals(Object)}
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (role == null ? 0 : role.hashCode());
        hashCode = 31 * hashCode + (feature == null ? 0 : feature.hashCode());
        hashCode = 31 * hashCode + (benefit == null ? 0 : benefit.hashCode());
        hashCode = 31 * hashCode + scenarios.hashCode();
        return hashCode;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[StoryDetails name=");
        buffer.append(name);
        buffer.append(", role=");
        buffer.append(role);
        buffer.append(", feature=");
        buffer.append(feature);
        buffer.append(", benefit=");
        buffer.append(benefit);
        buffer.append(", scenarios=");
        for (Iterator i = scenarios.iterator(); i.hasNext(); ){
            buffer.append("\n");
            ScenarioDetails scenario = (ScenarioDetails)i.next();
            buffer.append(scenario);
        }        
        buffer.append("\n");
        buffer.append("]");
        return buffer.toString();
    }
}

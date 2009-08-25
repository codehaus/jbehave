/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.codegen.domain;

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
        if (!super.equals(obj)) return false;
        if (!(obj instanceof StoryDetails)) return false;
        StoryDetails that = (StoryDetails) obj;
        return super.equals(obj)
			&& this.role.equals(that.role)
	        && this.feature.equals(that.feature)
	        && this.benefit.equals(that.benefit)
	        && this.scenarios.equals(that.scenarios);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append("\n");
        buffer.append(role);
        buffer.append("\n");
        buffer.append(feature);
        buffer.append("\n");
        buffer.append(benefit);
        buffer.append("\n\n");
        
        for (Iterator iter = scenarios.iterator(); iter.hasNext();) {
            buffer.append(iter.next().toString());
            buffer.append("\n");
        }
        return buffer.toString();
    }
}

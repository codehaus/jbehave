/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.codegen.domain;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDetails {
    public String name = "";
    public ContextDetails context = new ContextDetails();
    public BasicDetails event = new BasicDetails();
    public OutcomeDetails outcome = new OutcomeDetails();

	public ScenarioDetails() {
	}
	
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
        ScenarioDetails that = (ScenarioDetails)obj;
        
        return this.name.equals(that.name)
        	&& this.context.equals(that.context)
        	&& this.outcome.equals(that.outcome)
        	&& this.event.equals(that.event);
        
    }
    
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode + context.hashCode();
        hashCode = 31 * hashCode + event.hashCode();
        hashCode = 31 * hashCode + outcome.hashCode();
        return hashCode;
    }    
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ScenarioDetails name=");
        buffer.append(name);
        buffer.append(", context=");
        buffer.append(context);
        buffer.append(", event=");
        buffer.append(event);
        buffer.append(", outcome=");
        buffer.append(outcome);
        buffer.append("]");
        return buffer.toString();
    }
}

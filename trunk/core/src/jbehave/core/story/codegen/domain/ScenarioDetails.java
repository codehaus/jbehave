/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.codegen.domain;

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
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ScenarioDetails)) return false;
        
        ScenarioDetails that = (ScenarioDetails)obj;
        
        return this.name.equals(that.name)
        	&& this.context.equals(that.context)
        	&& this.outcome.equals(that.outcome)
        	&& this.event.equals(that.event);
        
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append("\n");
        buffer.append(context.toString());
        buffer.append(event.toString());
        buffer.append(outcome.toString());
        return buffer.toString();
    }
}

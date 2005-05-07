/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.codegen.domain;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDetails {
    private final ContextDetails context;
    private final BasicDetails event;
    private final OutcomeDetails outcome;
    private final String name;

    public ScenarioDetails(String name, ContextDetails context, BasicDetails event, OutcomeDetails outcome) {
        this.name = name;
        this.context = context;
        this.event = event;
        this.outcome = outcome;
    }
    
    public String getName() {
        return name;
    }
    
    public ContextDetails getContext() {
        return context;
    }
    
    public BasicDetails getEvent() {
        return event;
    }
    
    public OutcomeDetails getOutcome() {
        return outcome;
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
        buffer.append(";\n");
        buffer.append(context.toString());
        buffer.append(event.toString());
        buffer.append(outcome.toString());
        return buffer.toString();
    }
}

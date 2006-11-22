/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

/**
 * Represents a composite {@link Outcome}.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Outcomes extends ScenarioComponents implements Outcome {
	
    private final Outcome[] outcomes;
    
	public Outcomes(Outcome[] outcomes) {
        super(outcomes);
		this.outcomes = outcomes;
    }
    
    public Outcomes(Outcome outcome) {
        this(new Outcome[] {outcome});
    }
    
    public Outcomes(Outcome outcome1, Outcome outcome2) {
        this(new Outcome[] {outcome1, outcome2});
    }
    
    public Outcomes(Outcome outcome1, Outcome outcome2, Outcome outcome3) {
        this(new Outcome[] {outcome1, outcome2, outcome3});
    }
    
	public void setExpectationIn(final World world) {
		for(int i = 0; i < outcomes.length; i++) {
		    outcomes[i].setExpectationIn(world);
        }
	}

	public void verify(final World world) {
        for(int i = 0; i < outcomes.length; i++) {
            outcomes[i].verify(world);
        }
	}
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Outcomes ");
        for ( int i = 0; i < outcomes.length; i++ ){
            buffer.append(outcomes[i].getClass().getName());
            if ( i < outcomes.length - 1 ){
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

}

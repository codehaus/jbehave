package org.jbehave.scenario.fromtext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.jbehave.scenario.parser.StepParser;
import org.junit.Test;


public class StepParserBehaviour {

	private static final String NL = System.getProperty("line.separator");

	@Test
	public void shouldExtractGivensWhensAndThensFromSimpleScenarios() {
		StepParser parser = new StepParser();
		List<String> stringSteps = parser.findSteps(
				"Given a scenario" + NL + 
				"When I parse it" + NL + 
				"Then I should get steps");
		
		ensureThat(stringSteps.get(0), equalTo("Given a scenario"));
		ensureThat(stringSteps.get(1), equalTo("When I parse it"));
		ensureThat(stringSteps.get(2), equalTo("Then I should get steps"));
	}
	
	@Test
	public void shouldExtractGivensWhensAndThensFromMultilineScenarios() {
		StepParser parser = new StepParser();
		List<String> stringSteps = parser.findSteps(
				"Given a scenario" + NL +
				"with this line" + NL +
				"When I parse it" + NL +
				"with another line" + NL + NL +
				"Then I should get steps" + NL +
				"without worrying about lines" + NL +
				"or extra white space between or after steps" + NL + NL);
		
		ensureThat(stringSteps.get(0), equalTo("Given a scenario" + NL +
				"with this line" ));
		ensureThat(stringSteps.get(1), equalTo("When I parse it" + NL +
				"with another line"));
		ensureThat(stringSteps.get(2), equalTo("Then I should get steps" + NL +
				"without worrying about lines" + NL +
				"or extra white space between or after steps"));
	}
	
	
}

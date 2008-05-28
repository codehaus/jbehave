package org.jbehave.scenario.steps;

import static org.jbehave.Ensure.ensureThat;

import org.junit.Test;


public class DollarArgToRegExpBehaviour {

	@Test
	public void shouldReplaceAllDollarArgumentsWithCaptures() {
		DollarToCaptureRegexpBuilder argToRegExp = new DollarToCaptureRegexpBuilder();
		ensureThat(argToRegExp.replaceArgsWithCapture("a house with $numberOfDoors doors and $some windows").matcher("a house with 3 doors and 4 windows").matches());
		ensureThat(argToRegExp.replaceArgsWithCapture("the house on $street").matcher("the house on Easy Street").matches());
		ensureThat(argToRegExp.replaceArgsWithCapture("$number houses").matcher("5 houses").matches());
		ensureThat(argToRegExp.replaceArgsWithCapture("my house").matcher("my house").matches());
	}
	
	@Test
	public void shouldEscapeExistingBrackets() {
		DollarToCaptureRegexpBuilder argToRegExp = new DollarToCaptureRegexpBuilder();
		ensureThat(argToRegExp.replaceArgsWithCapture("I toggle the cell at ($column, $row)").matcher("I toggle the cell at (3, 4)").matches());
	}
}

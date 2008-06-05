package org.jbehave.scenario.steps;

import static org.jbehave.Ensure.ensureThat;

import org.junit.Test;


public class DollarStepPatternBuilderBehaviour {

	@Test
	public void shouldReplaceAllDollarArgumentsWithCaptures() {
		StepPatternBuilder builder = new DollarStepPatternBuilder();
		ensureThat(builder.buildPattern("a house with $numberOfDoors doors and $some windows").matcher("a house with 3 doors and 4 windows").matches());
		ensureThat(builder.buildPattern("the house on $street").matcher("the house on Easy Street").matches());
		ensureThat(builder.buildPattern("$number houses").matcher("5 houses").matches());
		ensureThat(builder.buildPattern("my house").matcher("my house").matches());
	}
	
	@Test
	public void shouldEscapeExistingBrackets() {
		StepPatternBuilder matcher = new DollarStepPatternBuilder();
		ensureThat(matcher.buildPattern("I toggle the cell at ($column, $row)").matcher("I toggle the cell at (3, 4)").matches());
	}

}

package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class DollarStepPatternBuilderBehaviour {

	private static final String NL = System.getProperty("line.separator");

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
	
	@Test
	public void shouldNotCareSoMuchAboutWhitespace() {
		StepPatternBuilder matcher = new DollarStepPatternBuilder();
		Pattern pattern = matcher.buildPattern("The grid looks like $grid");
		
		// Given an argument on a new line
		Matcher matched = pattern.matcher(
				"The grid looks like" + NL +
				".." + NL +
				".." + NL
				);
		ensureThat(matched.matches());
		ensureThat(matched.group(1), equalTo(
				".." + NL +
				".." + NL));
		
		// Given an argument on a new line with extra spaces
		matched = pattern.matcher(
				"The grid looks like " + NL +
				".." + NL +
				".." + NL
				);
		ensureThat(matched.matches());
		ensureThat(matched.group(1), equalTo(
				".." + NL +
				".." + NL));
		
		// Given an argument with extra spaces
		matched = pattern.matcher(
				"The grid looks like  .");
		ensureThat(matched.matches());
		ensureThat(matched.group(1), equalTo(
				"."));
		
	}

}

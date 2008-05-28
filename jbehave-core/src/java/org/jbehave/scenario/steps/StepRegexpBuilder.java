package org.jbehave.scenario.steps;

import java.util.regex.Pattern;

/**
 * Turns a template step, as provided in the annotations, into a 
 * pattern which will match real steps that match the template.
 * 
 * eg: When I give $money to $name
 * becomes
 * When I give (.*) to (.*)
 */
public interface StepRegexpBuilder {

	Pattern replaceArgsWithCapture(String matchThis);

}

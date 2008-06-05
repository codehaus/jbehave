package org.jbehave.scenario.steps;

import java.util.regex.Pattern;

/**
 * Builds a regex pattern from a template step, as provided in the annotations,
 * which will in turn match real steps conforming to the template. Eg: "When I
 * give $money to $name" becomes "When I give (.*) to (.*)"
 */
public interface StepPatternBuilder {

    Pattern buildPattern(String matchThis);

}

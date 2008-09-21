package org.jbehave.scenario.steps;

import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;

/**
 * <p>
 * Class allowing steps functionality to be fully configurable, while providing
 * default values for most commonly-used cases.
 * </p>
 */
public class StepsConfiguration {
    private StepPatternBuilder patternBuilder;
    private StepMonitor monitor;
    private ParameterConverters parameterConverters;
    private String[] startingWords;

    public StepsConfiguration() {
        this("Given", "When", "Then", "And");
    }

    public StepsConfiguration(String... startingWords) {
        this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(), new ParameterConverters(), startingWords);
    }

    public StepsConfiguration(StepPatternBuilder patternBuilder, StepMonitor monitor,
            ParameterConverters parameterConverters, String... startingWords) {
        this.patternBuilder = patternBuilder;
        this.monitor = monitor;
        this.parameterConverters = parameterConverters;
        this.startingWords = startingWords;
    }

    public StepPatternBuilder getPatternBuilder() {
        return patternBuilder;
    }

    public StepMonitor getMonitor() {
        return monitor;
    }

    public ParameterConverters getParameterConverters() {
        return parameterConverters;
    }

    public String[] getStartingWords() {
        return startingWords;
    }

}

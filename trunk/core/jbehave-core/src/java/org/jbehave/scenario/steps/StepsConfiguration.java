package org.jbehave.scenario.steps;

import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;

import com.thoughtworks.paranamer.NullParanamer;
import com.thoughtworks.paranamer.Paranamer;

/**
 * <p>
 * Class allowing steps functionality to be fully configurable, while providing
 * default values for most commonly-used cases.
 * </p>
 * <p>
 * Configuration dependencies can be provided either via constructor or via
 * setters (called use* methods to underline that a default value of the
 * dependency is always set, but can be overridden). The use methods allow to
 * override the dependencies one by one and play nicer with a Java hierarchical
 * structure, in that does allow the use of non-static member variables.
 * </p>
 */
public class StepsConfiguration {

	public static final String[] DEFAULT_STARTING_WORDS = new String[] {
			"Given", "When", "Then", "And" };

	private StepPatternBuilder patternBuilder;
	private StepMonitor monitor;
	private Paranamer paranamer;
	private ParameterConverters parameterConverters;
	private String[] startingWords;

	public StepsConfiguration() {
		this(DEFAULT_STARTING_WORDS);
	}

	public StepsConfiguration(String... startingWords) {
		this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(),
				new NullParanamer(), new ParameterConverters(), startingWords);
	}

	public StepsConfiguration(ParameterConverters converters) {
		this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(),
				new NullParanamer(), converters, DEFAULT_STARTING_WORDS);
	}

	public StepsConfiguration(StepPatternBuilder patternBuilder,
			StepMonitor monitor, Paranamer paranamer,
			ParameterConverters parameterConverters, String... startingWords) {
		this.patternBuilder = patternBuilder;
		this.monitor = monitor;
		this.paranamer = paranamer;
		this.parameterConverters = parameterConverters;
		this.startingWords = startingWords;
	}

	public StepPatternBuilder getPatternBuilder() {
		return patternBuilder;
	}

	public void usePatternBuilder(StepPatternBuilder patternBuilder) {
		this.patternBuilder = patternBuilder;
	}

	public StepMonitor getMonitor() {
		return monitor;
	}

	public void useMonitor(StepMonitor monitor) {
		this.monitor = monitor;
	}

	public Paranamer getParanamer() {
		return paranamer;
	}

	public void useParanamer(Paranamer paranamer) {
		this.paranamer = paranamer;
	}

	public ParameterConverters getParameterConverters() {
		return parameterConverters;
	}

	public void useParameterConverters(ParameterConverters parameterConverters) {
		this.parameterConverters = parameterConverters;
	}

	public String[] getStartingWords() {
		return startingWords;
	}

	public void useStartingWords(String... startingWords) {
		this.startingWords = startingWords;
	}

}

package org.jbehave.scenario.steps;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.i18n.I18nKeyWords;
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

	private StepPatternBuilder patternBuilder;
	private StepMonitor monitor;
	private Paranamer paranamer;
	private ParameterConverters parameterConverters;
	private KeyWords keywords;
	private String[] startingWords;

	public StepsConfiguration() {
		this(new I18nKeyWords());
	}

	public StepsConfiguration(KeyWords keywords) {
		this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(),
				new NullParanamer(), new ParameterConverters(), keywords);
	}

	public StepsConfiguration(ParameterConverters converters) {
		this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(),
				new NullParanamer(), converters, new I18nKeyWords());
	}

	public StepsConfiguration(StepPatternBuilder patternBuilder,
			StepMonitor monitor, Paranamer paranamer,
			ParameterConverters parameterConverters, KeyWords keywords) {
		this.patternBuilder = patternBuilder;
		this.monitor = monitor;
		this.paranamer = paranamer;
		this.parameterConverters = parameterConverters;
		this.keywords = keywords;
		this.startingWords = startingWordsFrom(this.keywords);
	}
	
	public StepsConfiguration(String... startingWords) {
		this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(),
				new NullParanamer(), new ParameterConverters(), startingWords);
	}
	
	public StepsConfiguration(StepPatternBuilder patternBuilder,
			StepMonitor monitor, Paranamer paranamer,
			ParameterConverters parameterConverters, String... startingWords) {
		this.patternBuilder = patternBuilder;
		this.monitor = monitor;
		this.paranamer = paranamer;
		this.parameterConverters = parameterConverters;
		this.keywords = new I18nKeyWords();
		this.startingWords = startingWords;
	}

	protected String[] startingWordsFrom(KeyWords keywords) {
		return new String[]{keywords.given(), keywords.when(), keywords.then(), keywords.and()};
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
	
	public KeyWords getKeywords() {
		return keywords;
	}

	public void useKeyWords(KeyWords keywords) {
		this.keywords = keywords;
		this.startingWords = startingWordsFrom(this.keywords);
	}

}

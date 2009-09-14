package org.jbehave.examples.trader;

import java.util.Locale;

import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.i18n.I18nKeyWords;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;

public class ItTraderScenario extends JUnitScenario {

	public ItTraderScenario() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public ItTraderScenario(final ClassLoader classLoader) {
		super(new PropertyBasedConfiguration() {
			@Override
			public ScenarioDefiner forDefiningScenarios() {
				// use underscored camel case scenario files with extension ".scenario"
				return new ClasspathScenarioDefiner(
						new UnderscoredCamelCaseResolver(".scenario"),
						new PatternScenarioParser(this), classLoader);
			}

			@Override
			public ScenarioReporter forReportingScenarios() {
				// report outcome in Italian (to System.out)
				return new PrintStreamScenarioReporter(new I18nKeyWords(new Locale("it")));
			}

			@Override
			public KeyWords keywords() {
				// use Italian for keywords
				return new I18nKeyWords(new Locale("it"));
			}

		}, new ItTraderSteps(classLoader));
	}

}

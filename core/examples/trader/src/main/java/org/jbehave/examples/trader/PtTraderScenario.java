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

public class PtTraderScenario extends JUnitScenario {

	public PtTraderScenario() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public PtTraderScenario(final ClassLoader classLoader) {
		super(new PropertyBasedConfiguration() {
			@Override
			public ScenarioDefiner forDefiningScenarios() {
				// use underscored camel case scenario files with extension ".cenario"
				return new ClasspathScenarioDefiner(
						new UnderscoredCamelCaseResolver(".cenario"),
						new PatternScenarioParser(this), classLoader);
			}

			@Override
			public ScenarioReporter forReportingScenarios() {
				// report outcome in Brazilian Portuguese (to System.out)
				return new PrintStreamScenarioReporter(new I18nKeyWords(new Locale("pt")));
			}

			@Override
			public KeyWords keywords() {
				// use Brazilian Portuguese for keywords
				return new I18nKeyWords(new Locale("pt"));
			}

		}, new PtTraderSteps(classLoader));
	}

}

package org.jbehave.examples.trader;

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


public class TraderScenario extends JUnitScenario {

    public TraderScenario() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TraderScenario(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
            public ScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(".scenario"), new PatternScenarioParser(this), classLoader);
            }

			@Override
			public ScenarioReporter forReportingScenarios() {
				return new PrintStreamScenarioReporter();
			}

			@Override
			public KeyWords keywords() {
				return new I18nKeyWords();
			}
            
        }, new TraderSteps(classLoader)); 
    }

}

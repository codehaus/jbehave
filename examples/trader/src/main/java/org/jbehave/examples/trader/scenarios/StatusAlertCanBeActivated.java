package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;


public class StatusAlertCanBeActivated extends Scenario {

    public StatusAlertCanBeActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public StatusAlertCanBeActivated(final ClassLoader classLoader) {
        super(new MostUsefulConfiguration() {
            public ScenarioDefiner forDefiningScenarios() {
                return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(".scenario"), classLoader, new PatternScenarioParser(new PropertyBasedConfiguration()));
            }
        }, new StockSteps(10.0));
    }

}

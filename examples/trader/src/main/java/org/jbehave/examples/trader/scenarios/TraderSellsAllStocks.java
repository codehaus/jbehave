package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;


public class TraderSellsAllStocks extends Scenario {

    public TraderSellsAllStocks() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TraderSellsAllStocks(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
            public ScenarioDefiner forDefiningScenarios() {
                return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(".scenario"), classLoader, new PatternScenarioParser());
            }
        }, new StockSteps(10.0));
    }

}

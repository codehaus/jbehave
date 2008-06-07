package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ScenarioFileLoader;

public class AlertStatusCanBeActivated extends Scenario {

    public AlertStatusCanBeActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public AlertStatusCanBeActivated(ClassLoader classLoader) {
        super(new ScenarioFileLoader(classLoader), new StockSteps());
    }

}

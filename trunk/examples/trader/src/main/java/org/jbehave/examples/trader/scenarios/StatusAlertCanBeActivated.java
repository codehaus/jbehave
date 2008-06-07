package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ScenarioFileLoader;

public class StatusAlertCanBeActivated extends Scenario {

    public StatusAlertCanBeActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public StatusAlertCanBeActivated(ClassLoader classLoader) {
        super(new ScenarioFileLoader(classLoader), new StockSteps());
    }

}

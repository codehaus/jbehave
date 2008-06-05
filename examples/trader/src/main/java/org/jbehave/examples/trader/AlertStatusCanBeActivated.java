package org.jbehave.examples.trader;

import org.jbehave.examples.trader.steps.StockSteps;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ScenarioFileLoader;

public class AlertStatusCanBeActivated extends Scenario {

    public AlertStatusCanBeActivated(ClassLoader classLoader) {
        super(new ScenarioFileLoader(classLoader), new StockSteps());
    }

}

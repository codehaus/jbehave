package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;


public class StatusAlertIsNeverActivated extends Scenario {

    public StatusAlertIsNeverActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public StatusAlertIsNeverActivated(ClassLoader classLoader) {
        super(new ScenarioFileLoader(new UnderscoredCamelCaseResolver(".scenario"), classLoader), new StockSteps(100.0));
    }

}

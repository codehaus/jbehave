package org.jbehave.examples.trader;

import org.jbehave.examples.trader.steps.StockSteps;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.FileFinder;

public class AlertStatusCanBeActivated extends Scenario {

    public AlertStatusCanBeActivated(ClassLoader classLoader) {
        super(new FileFinder(classLoader), new StockSteps());
    }

}

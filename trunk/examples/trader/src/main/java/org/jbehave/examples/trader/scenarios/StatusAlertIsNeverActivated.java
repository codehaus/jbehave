package org.jbehave.examples.trader.scenarios;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;


public class StatusAlertIsNeverActivated extends Scenario {

    public StatusAlertIsNeverActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public StatusAlertIsNeverActivated(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
        	@Override
        	public ScenarioFileLoader forDefiningScenarios() {
        		return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(".scenario"), classLoader, new PatternStepParser());
        	}
        }, new StockSteps(100.0));
    }

}

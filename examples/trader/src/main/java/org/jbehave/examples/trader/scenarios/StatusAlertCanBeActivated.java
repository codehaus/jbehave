package org.jbehave.examples.trader.scenarios;

import org.jbehave.OurTechnique;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternStepParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;


public class StatusAlertCanBeActivated extends Scenario {

    public StatusAlertCanBeActivated() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public StatusAlertCanBeActivated(final ClassLoader classLoader) {
        super(new OurTechnique() {
			public ScenarioFileLoader forDefiningScenarios() {
				return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(".scenario"), classLoader, new PatternStepParser());
			}
        }, new StockSteps(10.0));
    }

}

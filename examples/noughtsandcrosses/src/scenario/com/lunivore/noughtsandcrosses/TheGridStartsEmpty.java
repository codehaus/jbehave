package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class TheGridStartsEmpty extends Scenario {

    public TheGridStartsEmpty() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TheGridStartsEmpty(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ScenarioFileLoader forDefiningScenarios() {
                return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(), classLoader,
                        new PatternScenarioParser(this));
            }
        }, new GridSteps());
    }
}

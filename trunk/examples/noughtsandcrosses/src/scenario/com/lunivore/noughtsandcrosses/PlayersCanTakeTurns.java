package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class PlayersCanTakeTurns extends Scenario {

    public PlayersCanTakeTurns() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PlayersCanTakeTurns(final ClassLoader classLoader) {
        super(new MostUsefulConfiguration() {
            public ScenarioFileLoader forDefiningScenarios() {
                return new ScenarioFileLoader(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(this),
                        classLoader);
            }
        }, new GridSteps());
    }
}

package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class PlayersCanTakeTurns extends JUnitScenario {

    public PlayersCanTakeTurns() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PlayersCanTakeTurns(final ClassLoader classLoader) {
        super(new MostUsefulConfiguration() {
            public ClasspathScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(this),
                        classLoader);
            }
        }, new GridSteps());
    }
}

package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class ThreeInARowWins extends JUnitScenario {

    public ThreeInARowWins() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ThreeInARowWins(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ClasspathScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(this),
                        classLoader);
            }
        }, new GridSteps());
    }
}

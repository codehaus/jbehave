package com.lunivore.noughtsandcrosses.util;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.noughtsandcrosses.steps.BeforeAndAfterSteps;
import com.lunivore.noughtsandcrosses.steps.GridSteps;

public abstract class NoughtsAndCrossesScenario extends Scenario {

	/**
	 * The only reason this classLoader is here is to support Maven.
	 */
	public NoughtsAndCrossesScenario(final ClassLoader classLoader) {
		this(classLoader, new OAndXUniverse());
	}
	
	public NoughtsAndCrossesScenario(final ClassLoader classLoader, OAndXUniverse universe) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ClasspathScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(this),
                        classLoader);
            }
        }, new GridSteps(universe), new BeforeAndAfterSteps(universe));
     }

}

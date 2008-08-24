package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.Scenario;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class ThreeInARowWins extends Scenario {
    
    public ThreeInARowWins() {
        super(new GridSteps());
    }

}

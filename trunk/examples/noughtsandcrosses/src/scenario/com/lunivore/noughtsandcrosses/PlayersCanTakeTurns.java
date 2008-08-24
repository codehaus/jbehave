package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.Scenario;

import com.lunivore.noughtsandcrosses.steps.GridSteps;

public class PlayersCanTakeTurns extends Scenario {

    public PlayersCanTakeTurns() {
        super(new GridSteps());
    }

}

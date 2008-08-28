package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.definition.KeyWords;

import com.lunivore.noughtsandcrosses.steps.LolCatzSteps;

/**
 * Checks that we can support scenarios written in other languages,
 * eg: lolcatz
 */
public class PlayersCanHazTurns extends Scenario {

    public PlayersCanHazTurns() {
        super(new MostUsefulConfiguration() {
            public KeyWords keywords() {
                return new KeyWords("I can haz", "Gief", "Wen", "Den", "And");
            }
        }, new LolCatzSteps());
    }
}

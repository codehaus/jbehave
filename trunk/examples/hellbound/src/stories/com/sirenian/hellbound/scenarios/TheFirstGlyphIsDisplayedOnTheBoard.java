package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.ThePlayerStartsTheGame;
import com.sirenian.hellbound.givens.HellboundIsRunning;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;

public class TheFirstGlyphIsDisplayedOnTheBoard extends MultiStepScenario {

    public void assemble() {
        given(new HellboundIsRunning());
        when(new ThePlayerStartsTheGame());
        then(new TheGlyphShouldBeCentredAtTheTopOfThePit());
    }
}

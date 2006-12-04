package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;

public class ThePlayerSeesTheFirstGlyphMove extends MultiStepScenario {

    public void assemble() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new TimePasses());
        then(new TheGlyphShouldMoveDownwards());
    }

}

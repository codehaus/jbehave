package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;

public class ThePlayerSeesTheFirstGlyphMove extends HellboundScenario {

    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new TimePasses());
        then(new TheGlyphShouldMoveDownwards());
    }

}

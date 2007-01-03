package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheLeftKey;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeAgainstTheLeftWall;

public class TheGlyphIsAgainstTheLeftWall extends HellboundScenario {

    protected void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheLeftKey());
        when(new ThePlayerPressesTheLeftKey());
        then(new TheGlyphShouldBeAgainstTheLeftWall()); // sanity check
    }

}

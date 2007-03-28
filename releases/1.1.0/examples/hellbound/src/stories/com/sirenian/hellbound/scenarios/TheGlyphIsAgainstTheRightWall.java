package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheRightKey;

public class TheGlyphIsAgainstTheRightWall extends HellboundScenario {

    protected void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheRightKey());
        when(new ThePlayerPressesTheRightKey());
        then(new TheGlyphShouldBeAgainstTheRightWall()); // sanity check
    }

}

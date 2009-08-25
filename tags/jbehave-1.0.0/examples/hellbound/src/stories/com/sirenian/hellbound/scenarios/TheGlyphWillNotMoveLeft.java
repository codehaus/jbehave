package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheLeftKey;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeAgainstTheLeftWall;

public class TheGlyphWillNotMoveLeft extends HellboundScenario {

    protected void specifySteps() {
        given(new TheGlyphIsAgainstTheLeftWall());
        when(new ThePlayerPressesTheLeftKey());
        then(new TheGlyphShouldBeAgainstTheLeftWall());
    }

}

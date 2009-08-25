package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheRightKey;

public class TheGlyphWillNotMoveRight extends HellboundScenario {

    protected void specifySteps() {
        given(new TheGlyphIsAgainstTheRightWall());
        when(new ThePlayerPressesTheRightKey());
        then(new TheGlyphShouldBeAgainstTheRightWall());
    }

}

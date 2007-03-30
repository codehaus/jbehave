package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDownKey;
import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear;

public class TheGlyphWillNotMoveDown extends HellboundScenario {

    protected void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheDropKey());
        when(new ThePlayerPressesTheDownKey());
        then(new TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear(
                "..ZZ..." + NL +
                "...ZZ.." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "..XXX.." + NL +
                "...X..." + NL
                ));
    }

}

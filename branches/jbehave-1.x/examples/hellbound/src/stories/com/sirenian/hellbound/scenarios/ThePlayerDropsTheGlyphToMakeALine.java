package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheLineShouldDisappearAndTheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphToMakeALine extends HellboundScenario {

    protected void specifySteps() {
        given(new TheJunkHasAHoleAndAGlyphOfTheRightShapeIsAboveIt());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        then(new TheLineShouldDisappearAndTheNextGlyphShouldAppear());
    }

}

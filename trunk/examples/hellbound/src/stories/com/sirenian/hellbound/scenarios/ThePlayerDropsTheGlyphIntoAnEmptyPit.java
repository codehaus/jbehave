package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallToTheBottom;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphIntoAnEmptyPit extends HellboundScenario {

    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallToTheBottom());
        when(new TimePasses());                
        then(new TheGlyphSegmentsShouldBecomeJunk());
        then(new TheNextGlyphShouldAppear());
    }
}

package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallOntoTheJunk;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphOntoJunk extends HellboundScenario {

    public void specifySteps() {
        given(new ThePlayerDropsTheGlyphIntoAnEmptyPit());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallOntoTheJunk());
        then(new TheGlyphSegmentsShouldBecomeJunk());
        then(new TheNextGlyphShouldAppear());
    }
}

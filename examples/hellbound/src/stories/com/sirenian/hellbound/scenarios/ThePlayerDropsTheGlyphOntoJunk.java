package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphOntoJunk extends MultiStepScenario {

    public void assemble() {
        given(new ThePlayerDropsTheGlyphIntoAnEmptyPit());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallOntoTheJunk());
        then(new TheGlyphSegmentsShouldBecomeJunk());
        then(new TheNextGlyphShouldAppear());
    }
}

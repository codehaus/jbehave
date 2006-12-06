package com.sirenian.hellbound.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallToTheBottom;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphIntoAnEmptyPit extends MultiStepScenario {

    public void specify() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallToTheBottom());
        when(new TimePasses());                
        then(new TheGlyphSegmentsShouldBecomeJunk());
        then(new TheNextGlyphShouldAppear());
    }
}

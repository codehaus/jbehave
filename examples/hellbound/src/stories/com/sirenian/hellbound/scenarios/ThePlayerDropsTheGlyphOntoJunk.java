package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

public class ThePlayerDropsTheGlyphOntoJunk extends ScenarioUsingMiniMock {

    public ThePlayerDropsTheGlyphOntoJunk() {
        super(given(new ThePlayerDropsTheGlyphIntoAnEmptyPit()),
               when(new ThePlayerPressesTheDropKey()),
               then(new TheGlyphShouldFallOntoTheJunk(),
                    new TheGlyphSegmentsShouldBecomeJunk(),
                    new TheNextGlyphShouldAppear()));
    }
}

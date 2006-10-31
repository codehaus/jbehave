package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallToTheBottom;
import com.sirenian.hellbound.outcomes.TheNextGlyphShouldAppear;

public class ThePlayerDropsTheGlyphIntoAnEmptyPit extends ScenarioUsingMiniMock {

    public ThePlayerDropsTheGlyphIntoAnEmptyPit() {
        super("The player drops the glyph into an empty pit",
                "The player drops the glyph",
                given(new TheFirstGlyphIsDisplayedOnTheBoard()),
                step(
                	step(
                			when(new ThePlayerPressesTheDropKey()),                
                			then(new TheGlyphShouldFallToTheBottom())),
                    step(
                    		when(new TimePasses()),
            		        then(new TheGlyphSegmentsShouldBecomeJunk(),
            		             new TheNextGlyphShouldAppear()))));
    }
}

package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropButton;
import com.sirenian.hellbound.givens.TheFirstGlyphIsDisplayedOnTheBoard;
import com.sirenian.hellbound.outcomes.TheGlyphSegmentsShouldBecomeJunk;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallToTheBottom;

public class ThePlayerDropsTheGlyphIntoAnEmptyPit extends ScenarioUsingMiniMock {

    public ThePlayerDropsTheGlyphIntoAnEmptyPit() {
        super("The player drops the glyph into an empty pit",
                "The player drops the glyph",
                new TheFirstGlyphIsDisplayedOnTheBoard(),
                new ThePlayerPressesTheDropButton(),
                new Outcomes(
                        new TheGlyphShouldFallToTheBottom(),
                        new TheGlyphSegmentsShouldBecomeJunk(),
                        new TheNextGlyphShouldAppear()));
    }

}

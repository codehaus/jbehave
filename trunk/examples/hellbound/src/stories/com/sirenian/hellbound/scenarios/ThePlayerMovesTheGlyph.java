package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.events.ThePlayerPressesTheDownButton;
import com.sirenian.hellbound.events.ThePlayerPressesTheLeftKey;
import com.sirenian.hellbound.events.ThePlayerPressesTheRightKey;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveRight;
import com.sirenian.hellbound.outcomes.TheHeartbeatShouldBeSkipped;

public class ThePlayerMovesTheGlyph extends ScenarioUsingMiniMock {

    public ThePlayerMovesTheGlyph() {
        super(given(new TheFirstGlyphIsDisplayedOnTheBoard()),
              when(new ThePlayerPressesTheRightKey()),
              then(new TheGlyphShouldMoveRight()),
              when(new ThePlayerPressesTheLeftKey()),
              then(new TheGlyphShouldBeCentredAtTheTopOfThePit()),
              when(new ThePlayerPressesTheDownButton()),
              then(new TheGlyphShouldMoveDownwards(),
                   new TheHeartbeatShouldBeSkipped()));
    }
}

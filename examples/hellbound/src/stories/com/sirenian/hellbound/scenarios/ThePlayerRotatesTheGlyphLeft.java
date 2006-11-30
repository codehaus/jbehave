package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesLeftRotate;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldTurnToOneQuarter;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToThreeQuarters;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToTwoQuarters;

import jbehave.core.story.domain.ScenarioUsingMiniMock;
import jbehave.core.story.domain.Step;

public class ThePlayerRotatesTheGlyphLeft extends ScenarioUsingMiniMock {

    public ThePlayerRotatesTheGlyphLeft() {
        super(given(new TheFirstGlyphIsDisplayedOnTheBoard()),
              step(new Step[] {
                step(when(new ThePlayerPressesLeftRotate()),
                        then(new TheGlyphShouldTurnToOneQuarter())),
                step(when(new ThePlayerPressesLeftRotate()),
                        then(new TheGlyphTurnsToTwoQuarters())),
                step(when(new ThePlayerPressesLeftRotate()),
                        then(new TheGlyphTurnsToThreeQuarters())),
                step(when(new ThePlayerPressesLeftRotate()),
                        then(new TheGlyphShouldBeCentredAtTheTopOfThePit()))}));            
    }
}

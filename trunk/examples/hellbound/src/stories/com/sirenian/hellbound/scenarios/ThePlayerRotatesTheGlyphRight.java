package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesRightRotate;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldTurnToOneQuarter;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToThreeQuarters;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToTwoQuarters;

import jbehave.core.story.domain.ScenarioUsingMiniMock;
import jbehave.core.story.domain.Step;

public class ThePlayerRotatesTheGlyphRight extends ScenarioUsingMiniMock {

    public ThePlayerRotatesTheGlyphRight() {
        super(given(new TheFirstGlyphIsDisplayedOnTheBoard()),
                step(new Step[] {
                    step(when(new ThePlayerPressesRightRotate()),
                         then(new TheGlyphTurnsToThreeQuarters())),
                    step(when(new ThePlayerPressesRightRotate()),
                         then(new TheGlyphTurnsToTwoQuarters())),
                    step(when(new ThePlayerPressesRightRotate()),
                         then(new TheGlyphShouldTurnToOneQuarter())),
                    step(when(new ThePlayerPressesRightRotate()),
                         then(new TheGlyphShouldBeCentredAtTheTopOfThePit()))
        }));        
    }
}

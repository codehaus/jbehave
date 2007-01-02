package com.sirenian.hellbound.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.ThePlayerPressesRightRotate;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldTurnToOneQuarter;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToThreeQuarters;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToTwoQuarters;

public class ThePlayerRotatesTheGlyphRight extends MultiStepScenario {

    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesRightRotate());
        then(new TheGlyphTurnsToThreeQuarters());
        when(new ThePlayerPressesRightRotate());
        then(new TheGlyphTurnsToTwoQuarters());
        when(new ThePlayerPressesRightRotate());
        then(new TheGlyphShouldTurnToOneQuarter());
        when(new ThePlayerPressesRightRotate());
        then(new TheGlyphShouldBeCentredAtTheTopOfThePit());
    }
}

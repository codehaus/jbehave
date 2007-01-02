package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesLeftRotate;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBeCentredAtTheTopOfThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldTurnToOneQuarter;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToThreeQuarters;
import com.sirenian.hellbound.outcomes.TheGlyphTurnsToTwoQuarters;

public class ThePlayerRotatesTheGlyphLeft extends HellboundScenario {

    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        
        when(new ThePlayerPressesLeftRotate());
        then(new TheGlyphShouldTurnToOneQuarter());
        
        when(new ThePlayerPressesLeftRotate());
        then(new TheGlyphTurnsToTwoQuarters());
        
        when(new ThePlayerPressesLeftRotate());
        then(new TheGlyphTurnsToThreeQuarters());
        
        when(new ThePlayerPressesLeftRotate());
        then(new TheGlyphShouldBeCentredAtTheTopOfThePit());
    }
}

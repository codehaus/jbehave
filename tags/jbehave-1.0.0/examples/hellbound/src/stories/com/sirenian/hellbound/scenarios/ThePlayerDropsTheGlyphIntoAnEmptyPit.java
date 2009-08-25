package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallToTheBottom;

public class ThePlayerDropsTheGlyphIntoAnEmptyPit extends HellboundScenario {
    
    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallToTheBottom());
        when(new TimePasses());                
        then(new TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear(
                "..ZZ..." + NL +
                "...ZZ.." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "..XXX.." + NL +
                "...X..." + NL
                ));
    }
}

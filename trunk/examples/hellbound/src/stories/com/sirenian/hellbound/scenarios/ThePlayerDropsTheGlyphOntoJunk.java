package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear;
import com.sirenian.hellbound.outcomes.TheGlyphShouldFallOntoTheJunk;

public class ThePlayerDropsTheGlyphOntoJunk extends HellboundScenario {

    public void specifySteps() {
        given(new ThePlayerDropsTheGlyphIntoAnEmptyPit());
        when(new ThePlayerPressesTheDropKey());
        then(new TheGlyphShouldFallOntoTheJunk());
        when(new TimePasses());
        then(new TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppear(
                "...SS.." + NL +
                "..SS..." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "..XX..." + NL +
                "...XX.." + NL +
                "..XXX.." + NL +
                "...X..." + NL));
    }
}

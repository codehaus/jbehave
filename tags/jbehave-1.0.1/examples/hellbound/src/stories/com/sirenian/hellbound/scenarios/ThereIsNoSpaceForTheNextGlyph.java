package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;

public class ThereIsNoSpaceForTheNextGlyph extends HellboundScenario {

    protected void specifySteps() {
        given(new TheJunkIsAlmostToTheTop());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        then(new TheGlyphShouldBecomeJunkAndTheNextGlyphShouldAppearOverlappingIt(
                "..L...." + NL +
                "..L...." + NL +
                "..LL..." + NL +
                "...XX.." + NL +
                "...X..." + NL +
                "...X..." + NL +
                "..XX..." + NL +
                "...XX.." + NL +
                "..XX..." + NL +
                "..XX..." + NL +
                "...XX.." + NL +
                "..XXX.." + NL +
                "...X..." + NL));
        then(new TheGameShouldBeOver());
    }

}

package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.ThePitShouldLookLike;

public class TheJunkIsAlmostToTheTop extends HellboundScenario {

    protected void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        then(new ThePitShouldLookLike( // sanity check
                "..ZZ..." + NL +
                "...ZZ.." + NL +
                "......." + NL +
                "......." + NL +
                "...X..." + NL +
                "...X..." + NL +
                "..XX..." + NL +
                "...XX.." + NL +
                "..XX..." + NL +
                "..XX..." + NL +
                "...XX.." + NL +
                "..XXX.." + NL +
                "...X..." + NL));
    }

}

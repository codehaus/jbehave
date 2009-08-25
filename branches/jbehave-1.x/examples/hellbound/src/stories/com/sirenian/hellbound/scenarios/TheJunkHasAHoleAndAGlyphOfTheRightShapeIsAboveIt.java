package com.sirenian.hellbound.scenarios;

import com.sirenian.hellbound.events.ThePlayerPressesLeftRotate;
import com.sirenian.hellbound.events.ThePlayerPressesRightRotate;
import com.sirenian.hellbound.events.ThePlayerPressesTheDropKey;
import com.sirenian.hellbound.events.ThePlayerPressesTheLeftKey;
import com.sirenian.hellbound.events.ThePlayerPressesTheRightKey;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.ThePitShouldLookLike;

public class TheJunkHasAHoleAndAGlyphOfTheRightShapeIsAboveIt extends
        HellboundScenario {

    protected void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        
        // T-shape to the left
        when(new ThePlayerPressesRightRotate());
        when(new ThePlayerPressesRightRotate());
        when(new ThePlayerPressesTheLeftKey());
        when(new ThePlayerPressesTheLeftKey());
        when(new ThePlayerPressesTheDropKey());        
        when(new TimePasses());
        
        // Z-shape next to it
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        
        // S-shape on the Z-shape
        when(new ThePlayerPressesTheRightKey());
        when(new ThePlayerPressesLeftRotate());
        when(new ThePlayerPressesTheDropKey());
        when(new TimePasses());
        
        // J-shape ready to be dropped in hole
        when(new ThePlayerPressesTheRightKey());
        when(new ThePlayerPressesTheRightKey());
        when(new ThePlayerPressesTheRightKey());
        
        then(new ThePitShouldLookLike(
                "......J" + NL +
                "......J" + NL +
                ".....JJ" + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "......." + NL +
                "...X..." + NL +
                "...XX.." + NL +
                ".XXXX.." + NL +
                "XXXXX.." + NL
                ));
    }

}

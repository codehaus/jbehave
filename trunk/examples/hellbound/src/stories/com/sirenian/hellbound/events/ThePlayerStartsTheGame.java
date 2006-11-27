package com.sirenian.hellbound.events;

import jbehave.core.story.domain.World;

import com.sirenian.hellbound.gui.ComponentNames;
import com.sirenian.hellbound.util.Logger;

public class ThePlayerStartsTheGame extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        clickButton(ComponentNames.START_GAME_BUTTON, world);
    }
}

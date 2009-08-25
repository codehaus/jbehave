package com.sirenian.hellbound.events;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.gui.ComponentNames;

public class ThePlayerStartsTheGame extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        clickButton(ComponentNames.START_GAME_BUTTON, world);
    }
}

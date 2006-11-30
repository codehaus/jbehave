package com.sirenian.hellbound.events;

import java.awt.event.KeyEvent;

import jbehave.core.story.domain.World;

public class ThePlayerPressesTheRightKey extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        pressKey(KeyEvent.VK_RIGHT, world);
    }

}

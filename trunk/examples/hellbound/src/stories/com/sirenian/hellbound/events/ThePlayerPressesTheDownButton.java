package com.sirenian.hellbound.events;

import java.awt.event.KeyEvent;

import org.jbehave.core.story.domain.World;


public class ThePlayerPressesTheDownButton extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        pressKey(KeyEvent.VK_DOWN, world);
    }

}

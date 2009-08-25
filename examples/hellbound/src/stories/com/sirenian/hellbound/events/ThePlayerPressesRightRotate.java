package com.sirenian.hellbound.events;

import org.jbehave.core.story.domain.World;

public class ThePlayerPressesRightRotate extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        pressKey('x', world);
    }

}

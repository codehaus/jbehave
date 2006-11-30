package com.sirenian.hellbound.events;

import jbehave.core.exception.PendingException;
import jbehave.core.story.domain.World;

public class ThePlayerPressesRightRotate extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        throw new PendingException();
    }

}

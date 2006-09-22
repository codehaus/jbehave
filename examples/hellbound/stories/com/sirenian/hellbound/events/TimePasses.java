package com.sirenian.hellbound.events;

import jbehave.core.story.domain.World;

import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.stories.WorldKey;


public class TimePasses extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        ForcedHeartbeat heartbeat = (ForcedHeartbeat) world.get(WorldKey.HEARTBEAT, null);
        heartbeat.causeBeat();
    }
}

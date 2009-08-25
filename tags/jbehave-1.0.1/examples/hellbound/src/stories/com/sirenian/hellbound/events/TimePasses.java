package com.sirenian.hellbound.events;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.stories.util.WorldKey;


public class TimePasses extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        ForcedHeartbeat heartbeat = (ForcedHeartbeat) world.get(WorldKey.HEARTBEAT, null);
        heartbeat.causeBeat();
    }
}

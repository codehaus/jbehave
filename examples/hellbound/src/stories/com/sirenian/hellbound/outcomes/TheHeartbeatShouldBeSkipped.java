package com.sirenian.hellbound.outcomes;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.stories.WorldKey;


public class TheHeartbeatShouldBeSkipped extends HellboundOutcome {
    protected void verifyAnyTimeIn(World world) {       
        ForcedHeartbeat heartbeat = (ForcedHeartbeat) world.get(WorldKey.HEARTBEAT);
        ensureThat(heartbeat.wasSkipped());
    }
}

package com.sirenian.hellbound.outcomes;

import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.stories.WorldKey;

import jbehave.core.story.domain.World;

public class TheHeartbeatShouldBeSkipped extends HellboundOutcome {

    public void setExpectationIn(World world) {}
    
    protected void verifyAnyTimeIn(World world) {       
        ForcedHeartbeat heartbeat = (ForcedHeartbeat) world.get(WorldKey.HEARTBEAT);
        ensureThat(heartbeat.wasSkipped());
    }
}

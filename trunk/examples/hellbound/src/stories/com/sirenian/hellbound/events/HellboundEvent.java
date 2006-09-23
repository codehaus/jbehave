package com.sirenian.hellbound.events;

import javax.swing.SwingUtilities;

import jbehave.core.story.domain.EventUsingMiniMock;
import jbehave.core.story.domain.World;

import com.sirenian.hellbound.engine.RequestQueue;
import com.sirenian.hellbound.engine.ThreadedRequestQueue;
import com.sirenian.hellbound.stories.WorldKey;

public abstract class HellboundEvent extends EventUsingMiniMock {

    private static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() { }
    };

    public void occurIn(World world) {
        occurAnyTimeIn(world);
        waitForAllQueuesToEmpty(world);
    }

    private void waitForAllQueuesToEmpty(World world) {
        RequestQueue queue = (ThreadedRequestQueue) world.get(WorldKey.REQUEST_QUEUE, null);
        queue.invokeAndWait(EMPTY_RUNNABLE);
        try {
            SwingUtilities.invokeAndWait(EMPTY_RUNNABLE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void occurAnyTimeIn(World world);

}

package com.sirenian.hellbound.stories;

import javax.swing.SwingUtilities;

import jbehave.core.story.domain.World;

import com.sirenian.hellbound.engine.RequestQueue;
import com.sirenian.hellbound.engine.ThreadedRequestQueue;

public class Idler extends jbehave.extensions.threaded.swing.Idler {

    private static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() { }
    };
    
	public void waitForIdle(World world) {
		waitForAllQueuesToEmpty(world);
		super.waitForIdle();
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
}

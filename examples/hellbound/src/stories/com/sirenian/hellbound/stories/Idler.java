package com.sirenian.hellbound.stories;

import jbehave.core.story.domain.World;

import com.sirenian.hellbound.engine.EngineQueue;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.ThreadedEngineQueue;
import com.sirenian.hellbound.gui.ThreadedSwingQueue;

public class Idler extends jbehave.extensions.threaded.swing.Idler {

    private static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() { }
        public String toString() { return "Idler"; }
    };
    
	public void waitForIdle(World world) {
		waitForAllQueuesToEmpty(world);
		super.waitForIdle();
	}
	
    private void waitForAllQueuesToEmpty(World world) {
        EngineQueue engineQueue = (ThreadedEngineQueue) world.get(WorldKey.ENGINE_QUEUE, null);
        GuiQueue guiQueue = (ThreadedSwingQueue) world.get(WorldKey.GUI_QUEUE, null);
        
        engineQueue.invokeAndWait(EMPTY_RUNNABLE);
        guiQueue.invokeAndWait(EMPTY_RUNNABLE);
    }
}

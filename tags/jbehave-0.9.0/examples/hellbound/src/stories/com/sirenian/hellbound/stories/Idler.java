package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.engine.EngineQueue;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.ThreadedEngineQueue;
import com.sirenian.hellbound.gui.ThreadedSwingQueue;
import com.sirenian.hellbound.util.Logger;

public class Idler extends org.jbehave.threaded.swing.Idler {
    
	public void waitForIdle(World world) {
		waitForAllQueuesToEmpty(world);
		super.waitForIdle();
	}
	
    private void waitForAllQueuesToEmpty(World world) {
    	Logger.debug(this, "Waiting for all queues to empty.");
        EngineQueue engineQueue = (ThreadedEngineQueue) world.get(WorldKey.ENGINE_QUEUE, null);
        GuiQueue guiQueue = (ThreadedSwingQueue) world.get(WorldKey.GUI_QUEUE, null);
        
        engineQueue.waitForIdle();
        guiQueue.waitForIdle();
    }
}

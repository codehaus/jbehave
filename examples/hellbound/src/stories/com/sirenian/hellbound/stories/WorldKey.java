package com.sirenian.hellbound.stories;

import org.jbehave.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.engine.ThreadedEngineQueue;
import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.gui.ThreadedSwingQueue;

public class WorldKey {
    
    public static final String HEARTBEAT = "Heartbeat: " + ForcedHeartbeat.class.getName(); 
    public static final String WINDOW_WRAPPER = "WindowWrapper: " + WindowWrapper.class.getName();
    public static final String PIT = "Pit: " + RenderedPit.class.getName();
    public static final String ENGINE_QUEUE = "EngineQueue: " + ThreadedEngineQueue.class.getName();
    public static final String GUI_QUEUE = "GuiQueue: " + ThreadedSwingQueue.class.getName();
    public static final String HELLBOUND = "Hellbound";

    private WorldKey(){};
}

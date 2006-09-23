package com.sirenian.hellbound.stories;

import jbehave.extensions.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.engine.ThreadedRequestQueue;
import com.sirenian.hellbound.givens.ForcedHeartbeat;
import com.sirenian.hellbound.gui.RenderedPit;

public class WorldKey {
    
    public static final String HEARTBEAT = "Heartbeat: " + ForcedHeartbeat.class.getName(); 
    public static final String WINDOW_WRAPPER = "WindowWrapper: " + WindowWrapper.class.getName();
    public static final String PIT = "Pit: " + RenderedPit.class.getName();
    public static final String REQUEST_QUEUE = "RequestQueue: " + ThreadedRequestQueue.class.getName();

    private WorldKey(){};
}

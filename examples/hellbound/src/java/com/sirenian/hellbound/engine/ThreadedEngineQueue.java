package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ThreadedEngineQueue extends ThreadedQueue implements EngineQueue {
    
    private GameRequestListener gameRequestListener = GameRequestListener.NULL;

    public ThreadedEngineQueue() {        
        startQueueThread("EngineRequestQueue");            
    }

	public void requestStart() {
	    queue(new Runnable() { 
            public void run() { 
                gameRequestListener.requestStart(); 
        }});
    }

    public void setGameRequestDelegate(GameRequestListener gameRequestListener) {
        this.gameRequestListener = gameRequestListener;
    }
}

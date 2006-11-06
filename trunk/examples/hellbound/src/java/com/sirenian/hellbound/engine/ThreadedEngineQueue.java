package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ThreadedEngineQueue extends ThreadedQueue implements EngineQueue {
    
    private GameRequestListener gameRequestListener = GameRequestListener.NULL;

    public ThreadedEngineQueue() {        
        super("EngineQueue");            
    }

	public void requestGameStart() {
	    queue(new Runnable() { 
            public void run() { gameRequestListener.requestGameStart(); }
            public String toString() {
                return "runnable gameRequestListener.requestStart()";
            }
        });
    }

    public void setGameRequestDelegate(GameRequestListener gameRequestListener) {
        this.gameRequestListener = gameRequestListener;
    }

    protected void perform(Runnable action) {
        action.run();
    }

}

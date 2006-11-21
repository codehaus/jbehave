package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.util.ThreadedQueue;

public class ThreadedEngineQueue extends ThreadedQueue implements EngineQueue {
    
    private GameRequestListener gameRequestListener = GameRequestListener.NULL;

    public ThreadedEngineQueue() {        
        super("EngineQueue");            
    }

	public void requestStartGame() {
	    queue(new Runnable() { 
            public void run() { gameRequestListener.requestStartGame(); }
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

    public void requestDropGlyph() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestDropGlyph(); }
            public String toString() {
                return "runnable gameRequestListener.requestDropGlyph()";
            }
        });
        
    }

}

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

    public void requestMoveGlyphDown() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestMoveGlyphDown(); }
            public String toString() {
                return "runnable gameRequestListener.requestMoveGlyphDown()";
            }
        });
    }

    public void requestMoveGlyphLeft() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestMoveGlyphLeft(); }
            public String toString() {
                return "runnable gameRequestListener.requestMoveGlyphLeft()";
            }
        });
    }

    public void requestMoveGlyphRight() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestMoveGlyphRight(); }
            public String toString() {
                return "runnable gameRequestListener.requestMoveGlyphRight()";
            }
        });
    }

    public void requestRotateGlyphLeft() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestRotateGlyphLeft(); }
            public String toString() {
                return "runnable gameRequestListener.requestRotateGlyphLeft()";
            }
        });
    }

    public void requestRotateGlyphRight() {
        queue(new Runnable() {
            public void run() { gameRequestListener.requestRotateGlyphRight(); }
            public String toString() {
                return "runnable gameRequestListener.requestRotateGlyphRight()";
            }
        });
    }

}

package com.sirenian.hellbound.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.util.ThreadedQueue;

public class ThreadedSwingQueue extends ThreadedQueue implements GuiQueue {

	public ThreadedSwingQueue() {
        super("SwingQueue");
    }

    private GameListener gameListener = GameListener.NULL;
    private GlyphListener glyphListener = GlyphListener.NULL;

    public void setGameListenerDelegate(GameListener listener) {
        this.gameListener = listener;
	}

	public void setGlyphListenerDelegate(GlyphListener listener) {
        glyphListener = listener;
	}

	public void reportGameStateChanged(final GameState state) {
		queue(new Runnable() {
	        public void run() { gameListener.reportGameStateChanged(state); }
            public String toString() { 
                return "changing game state to " + state; 
            }});
	}

	public void reportGlyphMovement(final GlyphType type, final Segments origin,
			final Segments destination) {
        queue(new Runnable() {
            public void run() { glyphListener.reportGlyphMovement(type, origin, destination); }
            public String toString() { 
                return "reporting glyph " + type + " movement from " + origin.toString() + " to " + destination.toString(); 
            }});
	}
	
    protected void perform(Runnable action) {
        try {
            SwingUtilities.invokeAndWait(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

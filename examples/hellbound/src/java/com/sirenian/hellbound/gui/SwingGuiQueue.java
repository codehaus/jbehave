package com.sirenian.hellbound.gui;

import javax.swing.SwingUtilities;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.ThreadedQueue;

public class SwingGuiQueue extends ThreadedQueue implements GuiQueue {

	protected SwingGuiQueue() {
		super("SwingGuiQueue");
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
	        public void run() {
	            gameListener.reportGameStateChanged(state);
	        }});
	}

	public void reportGlyphMovement(final GlyphType type, final Segments origin,
			final Segments destination) {
        queue(new Runnable() {
            public void run() {
                glyphListener.reportGlyphMovement(type, origin, destination);
        }});
	}
	
	protected void perform(Runnable runnable) {
	    SwingUtilities.invokeLater(runnable);
	}
}

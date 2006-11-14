package com.sirenian.hellbound.gui;

import javax.swing.SwingUtilities;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.engine.GuiQueue;

public class SwingGuiQueue implements GuiQueue {

	private GameListener gameListener = GameListener.NULL;
    private GlyphListener glyphListener = GlyphListener.NULL;

    public void setGameReportDelegate(GameListener listener) {
        this.gameListener = listener;
	}

	public void setGlyphReportDelegate(GlyphListener listener) {
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
	
	private void queue(Runnable runnable) {
	    SwingUtilities.invokeLater(runnable);
	}

    public void setGameListenerDelegate(GameListener listener) {
        // TODO Auto-generated method stub
        
    }

    public void setGlyphListenerDelegate(GlyphListener listener) {
        // TODO Auto-generated method stub
        
    }

    public void invokeAndWait(Runnable empty_runnable) {
        // TODO Auto-generated method stub
        
    }

    public void stop() {
        // TODO Auto-generated method stub
        
    }

}

package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.Glyph;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

public class Game implements GameRequestListener {

	private GameState state;
	
	private ListenerSet gameListeners;
	private ListenerSet glyphListeners;
	private ListenerNotifier stateNotifier;
	private final GlyphFactory factory;
	private final Heartbeat heartbeat;

	private final int width;
	private final int height;
	private int centre;

	private Glyph glyph;

	public Game(GlyphFactory factory, Heartbeat heartbeat, int width, int height) {
		this.factory = factory;
		this.heartbeat = heartbeat;
		this.width = width;
		this.height = height;
		gameListeners = new ListenerSet();
		glyphListeners = new ListenerSet();
		state = GameState.READY;
		stateNotifier = new ListenerNotifier() {
			public void notify(Listener listener) {
				((GameListener)listener).reportGameStateChanged(state);
			}			
		};
		centre = (int)(width + 1 / 2);
		
	}
	
	public void requestStart() {
        glyph = factory.nextGlyph(heartbeat, centre, glyphListeners);
		setState(GameState.RUNNING);
	}

	private void setState(GameState newState) {
		state = newState;
		gameListeners.notifyListeners(stateNotifier);
		
	}

	public void addListener(GameListener listener) {
		gameListeners.addListener(listener);
		stateNotifier.notify(listener);
	}

	public void addListener(GlyphListener listener) {
		glyphListeners.addListener(listener);
	}
	
	

}

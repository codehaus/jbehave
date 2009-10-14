package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphMovement;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;
import com.sirenian.hellbound.domain.glyph.Junk;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;
import com.sirenian.hellbound.util.Logger;

public class Game implements GameRequestListener {

	private static final int ONE_SECOND = 1000;
    private final GlyphFactory factory;
	private final Heartbeat heartbeat;
	private final CollisionDetector collisionDetector;
	private final ListenerSet gameListeners;
	private final ListenerSet glyphListeners;
	private final ListenerNotifier stateNotifier;
	private final HeartbeatListener heartbeatListener;

	private final int height;
    
	private GameState state;

	private LivingGlyph glyph = LivingGlyph.NULL;
	private Junk junk = Junk.NULL;
    private final int width;

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

		collisionDetector = new CollisionDetector() {
			public boolean collides(Segments segments) {
				if (segments.lowest() >= Game.this.height) { return true; }
                if (segments.leftmost() < 0) { return true; }
                if (segments.rightmost() >= Game.this.width) { return true; }
				if (junk.overlaps(segments)) { return true; }
				return false;
			}
    	};
        
        heartbeatListener = new HeartbeatListener() {
                    public void beat() {
                        if (state == GameState.RUNNING) {
                            Logger.debug(this, "Hearing heartbeat; moving glyph down");
                            requestGlyphMovement(GlyphMovement.DOWN);
                        }
                    }
                };
                
        this.heartbeat.addListener(heartbeatListener);
	}
	
	public void requestStartGame() {
		glyph = factory.nextGlyph(collisionDetector, glyphListeners);
		junk = factory.createJunk(glyphListeners);
        heartbeat.start(ONE_SECOND);
		setState(GameState.RUNNING);
	}

    private void resetGlyph() {
        glyph = factory.nextGlyph(collisionDetector, glyphListeners);
        if (glyph.getSegments().overlaps(junk.getSegments())) {
            setState(GameState.OVER);
            heartbeat.stop();
            glyph = LivingGlyph.NULL;
            junk = Junk.NULL;
        }
    }

	private void setState(GameState newState) {
		state = newState;
		gameListeners.notifyListeners(stateNotifier);
	}

	public void addGameListener(GameListener listener) {
		Logger.debug(this, "Adding game listener " + listener);
		gameListeners.addListener(listener);
		stateNotifier.notify(listener);
	}

	public void addGlyphListener(GlyphListener listener) {
		Logger.debug(this, "Adding glyph listener " + listener);
		glyphListeners.addListener(listener);
        junk.addListener(listener);
        glyph.addListener(listener);
	}

    public void requestGlyphMovement(GlyphMovement movement) {
        if (state == GameState.RUNNING) {
            Logger.debug(this, "Glyph movement " + movement + " requested");
            boolean result = movement.performOn(glyph);
            
            if (movement == GlyphMovement.DOWN) {
                if(!result) {
                    Logger.debug(this, "Could not move glyph down; junking it");
                    junk.absorb(glyph);
                    resetGlyph();
                } else {
                    heartbeat.skipNextBeat();
                }
            }
        }
    }
	
	

}
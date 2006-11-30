package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
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

	private final int height;
	private final int centre;
    
	private GameState state;

	private LivingGlyph glyph = LivingGlyph.NULL;
	private Junk junk = Junk.NULL;

	public Game(GlyphFactory factory, Heartbeat heartbeat, int width, int height) {
		this.factory = factory;
		this.heartbeat = heartbeat;
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
				if (junk.overlaps(segments)) { return true; }
				return false;
			}
    	};
        
        this.heartbeat.addListener(new HeartbeatListener() {
            public void beat() {
                if (state == GameState.RUNNING) {
                    moveGlyphDownOrJunkIt();
                }
            }
        });
    	
		centre = (int)((width - 1) / 2);
	}
	
	public void requestStartGame() {
		glyph = factory.nextGlyph(centre, collisionDetector, glyphListeners);
		junk = factory.createJunk(glyphListeners);
        heartbeat.start(ONE_SECOND);
		setState(GameState.RUNNING);
	}

    private void resetGlyph() {
        glyph = factory.nextGlyph(centre, collisionDetector, glyphListeners);
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

    public void requestDropGlyph() {
        Logger.debug(this, "Drop of glyph requested");
        if (state == GameState.RUNNING) {
        	glyph.drop();
        }
    }
    
    private void moveGlyphDownOrJunkIt() {
        if (!glyph.requestMoveDown()) {
            junk.absorb(glyph);
            resetGlyph();
        }
    }

    public void requestMoveGlyphDown() {
        moveGlyphDownOrJunkIt();
    }

    public void requestMoveGlyphLeft() {
        // TODO Auto-generated method stub
        
    }

    public void requestMoveGlyphRight() {
        // TODO Auto-generated method stub
        
    }
	
	

}

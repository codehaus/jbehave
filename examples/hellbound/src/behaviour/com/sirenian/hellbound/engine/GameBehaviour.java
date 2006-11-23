package com.sirenian.hellbound.engine;

import jbehave.core.exception.PendingException;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.domain.glyph.StubHeartbeat;
import com.sirenian.hellbound.domain.glyph.VerifiableGlyphListener;
import com.sirenian.hellbound.util.ListenerSet;

public class GameBehaviour extends UsingMiniMock {

    public void shouldMoveGlyphLowerOnHeartbeat() {
        StubHeartbeat heartbeat = new StubHeartbeat();
        VerifiableGlyphListener listener = new com.sirenian.hellbound.domain.glyph.VerifiableGlyphListener();
        
        Game game = new Game(new PseudoRandomGlyphFactory(), heartbeat, 7, 13);
        game.addGlyphListener(listener);
        game.requestStartGame();
        
        Segments firstSegments = listener.getSegments();
        heartbeat.beat();
        Segments secondSegments = listener.getSegments();

        ensureThat(secondSegments, eq(firstSegments.movedDown()));
    }
    
	public void shouldRunOnRequestStartAndInformListeners() throws Exception {
		
		Mock gameListener = mock(GameListener.class);
		gameListener.expects("reportGameStateChanged").once().with(GameState.READY);
		gameListener.expects("reportGameStateChanged").once().with(GameState.RUNNING);
		
		Game game = new Game(new PseudoRandomGlyphFactory(), new StubHeartbeat(), 7, 13);
		
		game.addGameListener((GameListener)gameListener);
		game.requestStartGame();
		
		verifyMocks();
	}
	
	public void shouldCreateANewGlyphWithListenersToItWhenGameIsStarted() throws Exception {
		Heartbeat heartbeat = new StubHeartbeat();
		
		Mock glyphFactoryMock = mock(GlyphFactory.class);
		Mock glyphListener = mock(GlyphListener.class);
		
		glyphFactoryMock.expects("nextGlyph")
            .with(new Constraint[] {eq(3), isA(CollisionDetector.class), isA(ListenerSet.class)})
                .will(returnValue(new LivingGlyph(GlyphType.T, CollisionDetector.NULL, 4)));
		
		Game game = new Game((GlyphFactory) glyphFactoryMock, heartbeat, 7, 13);
		game.addGlyphListener((GlyphListener)glyphListener);
		game.requestStartGame();
		
		verifyMocks();
	}
	
	public void shouldIgnoreRequestsToDropOrMoveGlyphWhenGameNotRunning() {
		throw new PendingException();
	}
}

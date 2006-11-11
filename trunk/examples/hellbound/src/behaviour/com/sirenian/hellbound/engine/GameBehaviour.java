package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.Glyph;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.domain.glyph.StubHeartbeat;
import com.sirenian.hellbound.util.ListenerSet;

public class GameBehaviour extends UsingMiniMock {

	public void shouldRunOnRequestStartAndInformListeners() throws Exception {
		
		Mock gameListener = mock(GameListener.class);
		gameListener.expects("reportGameStateChanged").once().with(GameState.READY);
		gameListener.expects("reportGameStateChanged").once().with(GameState.RUNNING);
		
		Game game = new Game(new PseudoRandomGlyphFactory(), new StubHeartbeat(), 7, 13);
		
		game.addGameListener((GameListener)gameListener);
		game.requestStartGame();
		
		verifyMocks();
	}
	
	public void shouldCauseGlyphToFallToBottomWhenDropped() throws Exception {
		
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
		
		glyphFactoryMock.expects("nextGlyph").with(new Constraint[] {eq(heartbeat), eq(4), isA(ListenerSet.class)}).will(returnValue(new LivingGlyph(heartbeat, GlyphType.T, 4)));
		
		Game game = new Game((GlyphFactory) glyphFactoryMock, heartbeat, 7, 13);
		game.addGlyphListener((GlyphListener)glyphListener);
		game.requestStartGame();
		
		verifyMocks();
	}
	
	public void shouldAddGlyphToJunkThenCreateNewGlyphWhenTheGlyphCannotMoveDown() throws Exception {
		StubHeartbeat heartbeat = new StubHeartbeat();
		
		Mock glyphFactoryMock = mock(GlyphFactory.class);
		Mock glyphListenerMock = mock(GlyphListener.class);
		
		Segments glyphSegments = new Segments(
        		new Segment(2, 11),
        		new Segment(3, 11),
        		new Segment(4, 11),
        		new Segment(3, 12)
		);
		Glyph glyphAtTheBottom = new Glyph(GlyphType.T, glyphSegments);
		
		
		glyphFactoryMock.expects("nextGlyph").with(eq(heartbeat), eq(4)).will(returnValue(glyphAtTheBottom));
		glyphListenerMock.expects("glyphMoved").with(new Constraint[] {eq(GlyphType.T), eq(Segments.EMPTY), eq(glyphSegments)});
		glyphListenerMock.expects("glyphMoved").with(new Constraint[] {eq(GlyphType.T), eq(glyphSegments), eq(Segments.EMPTY)});
		glyphListenerMock.expects("glyphMoved").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(glyphSegments)});
		
		Game game = new Game((GlyphFactory) glyphFactoryMock, heartbeat, 7, 13);
		game.addGlyphListener((GlyphListener) glyphListenerMock);
		
		game.requestStartGame();
		heartbeat.beat();
		
		verifyMocks();
		
	}
}

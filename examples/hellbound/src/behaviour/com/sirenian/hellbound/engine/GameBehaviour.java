package com.sirenian.hellbound.engine;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphMovement;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.Junk;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.domain.glyph.StubCollisionDetector;
import com.sirenian.hellbound.domain.glyph.StubHeartbeat;
import com.sirenian.hellbound.util.ListenerSet;

public class GameBehaviour extends UsingClassMock {

    public void shouldMoveGlyphLowerOnHeartbeat() {
        StubHeartbeat heartbeat = new StubHeartbeat();
        PseudoRandomGlyphFactory factory = new PseudoRandomGlyphFactory(42, 7, 13); // T, Z, S, J...
        Mock glyphListener = mock(GlyphListener.class);
        Game game = new Game(factory, heartbeat, 7, 13);
        game.addGlyphListener((GlyphListener) glyphListener);
        
        Segments initialSegments = GlyphType.T.getSegments(0).movedRight(3);
        
        glyphListener.stubs("reportGlyphMovement").with(eq(GlyphType.JUNK), anything(), anything());
        glyphListener.expects("reportGlyphMovement").with(eq(GlyphType.T), anything(), eq(initialSegments));
        glyphListener.expects("reportGlyphMovement").with(eq(GlyphType.T), eq(initialSegments), eq(initialSegments.movedDown()));
        game.requestStartGame();
        heartbeat.beat();
        
        verifyMocks();
    }
    
	public void shouldRunOnRequestStartAndStartHeartbeatAndInformListeners() throws Exception {
		
	    StubHeartbeat heartbeat = new StubHeartbeat();

        Mock gameListener = mock(GameListener.class);
		gameListener.expects("reportGameStateChanged").once().with(GameState.READY);
		gameListener.expects("reportGameStateChanged").once().with(GameState.RUNNING);
		
        Game game = new Game(new PseudoRandomGlyphFactory(7, 13), heartbeat, 7, 13);
		
		game.addGameListener((GameListener)gameListener);
		game.requestStartGame();
		
        ensureThat(heartbeat.isBeating());
        
		verifyMocks();
	}
	
	public void shouldCreateANewGlyphWithListenersToItWhenGameIsStarted() throws Exception {
		Heartbeat heartbeat = new StubHeartbeat();
		
		Mock glyphFactoryMock = mock(GlyphFactory.class);
		Mock glyphListener = mock(GlyphListener.class);
		
		glyphFactoryMock.expects("nextGlyph")
            .with(new Matcher[] {isA(CollisionDetector.class), isA(ListenerSet.class)})
                .will(returnValue(new LivingGlyph(GlyphType.T, CollisionDetector.NULL, 4)));
		
		Game game = new Game((GlyphFactory) glyphFactoryMock, heartbeat, 7, 13);
		game.addGlyphListener((GlyphListener)glyphListener);
		game.requestStartGame();
		
		verifyMocks();
	}
	
	public void shouldIgnoreRequestsToDropOrMoveGlyphWhenGameNotRunning() {
        StubHeartbeat heartbeat = new StubHeartbeat();
        PseudoRandomGlyphFactory factory = new PseudoRandomGlyphFactory(42, 7, 13); // T, Z, S, J...
        Mock glyphListener = mock(GlyphListener.class);
        Game game = new Game(factory, heartbeat, 7, 13);
        game.addGlyphListener((GlyphListener) glyphListener);
        
        glyphListener.stubs("reportGlyphMovement").never();
        
        heartbeat.beat();
        game.requestGlyphMovement(GlyphMovement.DOWN);
        game.requestGlyphMovement(GlyphMovement.LEFT);
        game.requestGlyphMovement(GlyphMovement.RIGHT);
        game.requestGlyphMovement(GlyphMovement.DROP);
        game.requestGlyphMovement(GlyphMovement.ROTATE_LEFT);
        game.requestGlyphMovement(GlyphMovement.ROTATE_RIGHT);
        
        verifyMocks();
	}
    
    public void shouldMoveGlyphWhenRequested() {
        AccessibleFactory factory  = new AccessibleFactory(7);
        Game game = new Game(factory, new StubHeartbeat(), 7, 13);
        Segments latestSegments = GlyphType.T.getSegments(0).movedRight(3);
        game.requestStartGame();
               
        game.requestGlyphMovement(GlyphMovement.ROTATE_LEFT);
        ensureThat(factory.glyph.getSegments(), eq(GlyphType.T.getSegments(1).movedRight(3)));
        
        game.requestGlyphMovement(GlyphMovement.ROTATE_RIGHT);
        ensureThat(factory.glyph.getSegments(), eq(GlyphType.T.getSegments(0).movedRight(3)));

        game.requestGlyphMovement(GlyphMovement.LEFT);
        ensureThat(factory.glyph.getSegments(), eq(latestSegments.movedLeft()));
        latestSegments = latestSegments.movedLeft();
        
        game.requestGlyphMovement(GlyphMovement.RIGHT);
        ensureThat(factory.glyph.getSegments(), eq(latestSegments.movedRight()));
        latestSegments = latestSegments.movedRight();

        game.requestGlyphMovement(GlyphMovement.DOWN);
        ensureThat(factory.glyph.getSegments(), eq(latestSegments.movedDown()));
        latestSegments = latestSegments.movedDown();
        
        verifyMocks();
    }
    
    public void shouldCauseGlyphSegmentsToBeAddedToPitThenCreateNewGlyphWhenGlyphCannotMoveDown() {
        // Given...
        
        StubHeartbeat heartbeat = new StubHeartbeat();
        Mock glyphFactoryMock = mock(GlyphFactory.class);
        
        LivingGlyph firstGlyph = new LivingGlyph(GlyphType.T, new StubCollisionDetector(13), 3);
        LivingGlyph secondGlyph = new LivingGlyph(GlyphType.S, CollisionDetector.NULL, 3);
        Junk junk = new Junk(7, 13);
        
        glyphFactoryMock.expects("nextGlyph").inOrder()
            .with(new Matcher[] {isA(CollisionDetector.class), isA(ListenerSet.class)})
            .will(returnValue(firstGlyph), returnValue(secondGlyph));
        glyphFactoryMock.expects("createJunk").with(isA(ListenerSet.class)).will(returnValue(junk));

        Segments segmentsForTShapeOnFloor = droppedToFloor(GlyphType.T.getSegments(0).movedRight(3), 13);
        
        Game game = new Game((GlyphFactory) glyphFactoryMock, heartbeat, 7, 13);
        game.requestStartGame();
        
        // When...
        game.requestGlyphMovement(GlyphMovement.DROP);
        heartbeat.beat();
        
        //Then...
        ensureThat(firstGlyph.getSegments(), eq(Segments.EMPTY));
        ensureThat(junk.getSegments(), eq(segmentsForTShapeOnFloor));
        verifyMocks();        
    }
    
    public void shouldEndGameWhenTheNewGlyphOverlapsTheJunk() {
        StubHeartbeat heartbeat = new StubHeartbeat();
        Game game = new Game(new PseudoRandomGlyphFactory(42, 7, 2), heartbeat, 7, 2); // pit is only 2 deep!
       
        Mock gameListener = mock(GameListener.class);
        game.requestStartGame();
        
        gameListener.expects("reportGameStateChanged").with(GameState.RUNNING);
        gameListener.expects("reportGameStateChanged").with(GameState.OVER);
        game.addGameListener((GameListener) gameListener);
        
        
        heartbeat.beat(); // first glyph falls; cannot add second glyph as it overlaps
        
        verifyMocks();
        
    }
    
    private Segments droppedToFloor(Segments segments, int floor) {
        Segments result = segments;
        while(result.lowest() < floor) {
            result = result.movedDown();
        }
        return result;
    }
    
    private static class AccessibleFactory extends PseudoRandomGlyphFactory {
        private AccessibleFactory(int width) {
            super(42, width, 13);
        }
        
        LivingGlyph glyph;
        public LivingGlyph nextGlyph(CollisionDetector detector, ListenerSet glyphListeners) {
            glyph = super.nextGlyph(detector, glyphListeners);
            return glyph;
        }
    }
}

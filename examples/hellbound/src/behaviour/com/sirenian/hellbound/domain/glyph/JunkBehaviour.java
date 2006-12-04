package com.sirenian.hellbound.domain.glyph;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;

public class JunkBehaviour extends UsingMiniMock {

	public void shouldInitiallyContainNoSegments() {
		Mock glyphListener = mock(GlyphListener.class);
		Junk junk = new Junk(9, 13);
		
		glyphListener.expects("reportGlyphMovement").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(Segments.EMPTY)});
		junk.addListener((GlyphListener)glyphListener);
		
		verifyMocks();
	}
	
	public void shouldAbsorbSegmentsFromOtherGlyphsAndKillThem() {
		Mock glyphListener = mock(GlyphListener.class);
        LivingGlyph glyph = new LivingGlyph(GlyphType.O, CollisionDetector.NULL, 4);
        Segments originalSegments = glyph.getSegments();

		Junk junk = new Junk(9, 13);
        glyphListener.expects("reportGlyphMovement").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(Segments.EMPTY)});
		glyphListener.expects("reportGlyphMovement").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(originalSegments)});

        junk.addListener((GlyphListener)glyphListener);
		junk.absorb(glyph);
        
		ensureThat(glyph.getSegments(), eq(Segments.EMPTY));
        ensureThat(junk.getSegments(), eq(originalSegments));
        
		verifyMocks();
	}
    
    public void shouldRemoveCompletedLinesWhenAbsorbingOtherGlyphsThenMoveAnySegmentsAboveLineDown() {
        Junk junk = new Junk(4, 13);
        
        LivingGlyph glyphToCheckThatOnlyWholeLinesAreRemoved = new LivingGlyph(GlyphType.T, CollisionDetector.NULL, 2);
        Segments expectedSegments = glyphToCheckThatOnlyWholeLinesAreRemoved.getSegments();
        
        LivingGlyph glyphForWholeLine = new LivingGlyph(GlyphType.I, new CollisionDetector(){
            public boolean collides(Segments segments) {
                return false;
            }}, 1);
        
        glyphForWholeLine.requestRotateLeft(); // now takes up whole row for width 4

        glyphForWholeLine.requestMoveDown();
        glyphForWholeLine.requestMoveDown();
        glyphForWholeLine.requestMoveDown();
        glyphForWholeLine.requestMoveDown(); // get it out of the way of the T
        
        junk.absorb(glyphToCheckThatOnlyWholeLinesAreRemoved);
        junk.absorb(glyphForWholeLine);
        
        ensureThat(junk.getSegments(), eq(expectedSegments.movedDown())); // absorbs whole row
        
        verifyMocks();
    }
}

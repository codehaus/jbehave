package com.sirenian.hellbound.domain.glyph;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;

public class JunkBehaviour extends UsingMiniMock {

	public void shouldInitiallyContainNoSegments() {
		Mock glyphListener = mock(GlyphListener.class);
		Junk junk = new Junk();
		
		glyphListener.expects("reportGlyphMovement").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(Segments.EMPTY)});
		junk.addListener((GlyphListener)glyphListener);
		
		verifyMocks();
	}
	
	public void shouldContainSegmentsWhichHaveBeenAdded() {
		Mock glyphListener = mock(GlyphListener.class);
		Segments segments = new Segments(
				new Segment(0, 5),
				new Segment(1, 5),
				new Segment(2, 5),
				new Segment(3, 5)
			);
		Junk junk = new Junk();
		junk.add(segments);
		
		glyphListener.expects("reportGlyphMovement").with(new Constraint[] {eq(GlyphType.JUNK), eq(Segments.EMPTY), eq(segments)});
		junk.addListener((GlyphListener)glyphListener);
		
		verifyMocks();
	}
}

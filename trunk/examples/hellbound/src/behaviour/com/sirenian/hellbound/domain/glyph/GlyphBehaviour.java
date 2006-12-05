package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.ListenerSet;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Matcher;
import jbehave.core.mock.Mock;

public class GlyphBehaviour extends UsingMiniMock {
	
    public void shouldExposeSegments() {
        Segments segments = new Glyph(GlyphType.O, GlyphType.O.getSegments(0)).getSegments();
        ensureThat(segments, eq(GlyphType.O.getSegments(0)));
    }
    
	public void shouldNotifyListenersOfMovement() throws Exception {
			
		Segments segments1 = new Segments(new Segment[] {new Segment(0, 0)});
		Segments segments2 = new Segments(new Segment[] {new Segment(0, 1)});
		
		Glyph glyph = new Glyph(GlyphType.O, GlyphType.O.getSegments(0));
		
		Mock listener1 = mock(GlyphListener.class);
		Mock listener2 = mock(GlyphListener.class);

		expectGlyphMovement(listener1, GlyphType.O.getSegments(0), GlyphType.O.getSegments(0));
		expectGlyphMovement(listener2, GlyphType.O.getSegments(0), GlyphType.O.getSegments(0));
		expectGlyphMovement(listener1, GlyphType.O.getSegments(0), segments1);		
		expectGlyphMovement(listener2, GlyphType.O.getSegments(0), segments1);		
		expectGlyphMovement(listener1, segments1, segments2);			
		expectGlyphMovement(listener2, segments1, segments2);		
		
		glyph.addListener((GlyphListener) listener1);
		glyph.addListener((GlyphListener) listener2);
		
		glyph.moveTo(segments1);
		glyph.moveTo(segments2);
		
		verifyMocks();
	}
	
	public void shouldAllowListenersToBeAddedAsSet() throws Exception {
		
		Glyph glyph = new Glyph(GlyphType.O, GlyphType.O.getSegments(0));
		
		Mock listener1 = mock(GlyphListener.class);
		Mock listener2 = mock(GlyphListener.class);

		expectGlyphMovement(listener1, GlyphType.O.getSegments(0), GlyphType.O.getSegments(0));
		expectGlyphMovement(listener2, GlyphType.O.getSegments(0), GlyphType.O.getSegments(0));
		
		ListenerSet listenerSet = new ListenerSet();
		listenerSet.addListener((GlyphListener) listener1);
		listenerSet.addListener((GlyphListener) listener2);
		
		glyph.addListeners(listenerSet);
		
		verifyMocks();
	}	

	public void shouldConfirmIfAnySegmentsOverlapWithGivenSegments() {
		Glyph glyph = new Glyph(GlyphType.O, GlyphType.O.getSegments(0));
		
		ensureThat(!glyph.overlaps(new Segments(new Segment[]{new Segment(-1, -1)})));
		ensureThat(glyph.overlaps(GlyphType.T.getSegments(0)));
	}

	private void expectGlyphMovement(Mock listener, Segments origin, Segments destination) {
		listener.expects("reportGlyphMovement").with(new Matcher[] {
				eq(GlyphType.O),
				eq(origin),
				eq(destination)
		});
	}
}

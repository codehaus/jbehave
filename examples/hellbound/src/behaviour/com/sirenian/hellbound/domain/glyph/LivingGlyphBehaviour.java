package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;

public class LivingGlyphBehaviour extends GlyphBehaviour {

	private VerifiableGlyphListener listener;
	private LivingGlyph glyph;

	private void setUp() {
		listener = new VerifiableGlyphListener();
		
		CollisionDetector detector = new StubCollisionDetector(new Segments(
				new Segment(3, 5),
				new Segment(4, 5),
				new Segment(5, 5),
				new Segment(6, 5)
		));
		
		glyph = new LivingGlyph(
				GlyphType.O,
				detector,
				4);
		
		glyph.addListener(listener);	
    }
	

	public void shouldDropUntilInCollision() {
		setUp();

		Segments firstSegments = listener.toLastSegments();
		
		glyph.drop();
		
		Segments secondSegments = listener.toLastSegments();
		Segments expectedSegments = firstSegments.movedDown().movedDown().movedDown();
		ensureThat(secondSegments, eq(expectedSegments));
	}
	
	public void shouldNotMoveIfInCollision() {
		setUp();
		
		CollisionDetector detector = new CollisionDetector() {
			public boolean collides(Segments segments) {
				return true;
			}
		};
		
		glyph = new LivingGlyph(
				GlyphType.O,
				detector,
				4);
		
		glyph.addListener(listener);
		
		Segments firstSegments = listener.toLastSegments();
		
		ensureThat(!glyph.requestMoveDown());
		
		Segments secondSegments = listener.toLastSegments();
		
		ensureThat(firstSegments, eq(secondSegments));
	}
    
    public void shouldReduceSegmentsToEmptyIfKilled() {
        setUp();
        Segments originalSegments = glyph.getSegments();
        
        glyph.kill();
        
        ensureThat(glyph.getSegments(), eq(Segments.EMPTY));
        ensureThat(listener.fromLastSegments(), eq(originalSegments));
        ensureThat(listener.toLastSegments(), eq(Segments.EMPTY));
    }
	

	

}

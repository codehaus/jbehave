package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;

public class LivingGlyph extends Glyph {
	
	private final CollisionDetector detector;

	public LivingGlyph( 
			GlyphType type,
			CollisionDetector detector,
			int centre) {
		
		super(type, type.getSegments(0).movedRight(centre));
		this.detector = detector;
		

		
		this.type = type;
		
	}
	
	public boolean requestMoveDown() {
		Segments newSegments = segments.movedDown();
		if (detector.collides(newSegments)) {
			return false;
		} else {
			moveTo(newSegments);
			return true;
		}
	}

	public void drop() {
		Segments segmentsToMoveTo = segments;
		Segments nextSegmentsDown = segments.movedDown();
		while (!detector.collides(nextSegmentsDown)) {
			segmentsToMoveTo = nextSegmentsDown;
			nextSegmentsDown = nextSegmentsDown.movedDown();
		}
		moveTo(segmentsToMoveTo);
	}
}

package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;
import com.sirenian.hellbound.util.ListenerSet;

public class LivingGlyph extends Glyph {
	
	public static final LivingGlyph NULL = new LivingGlyph(GlyphType.NULL, CollisionDetector.NULL, 0) {
        public void addListener(GlyphListener listener) {}
        public void addListeners(ListenerSet listeners) {}
    };
    
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

    public void kill() {
        moveTo(Segments.EMPTY);
    }
    
    public String toString() {
        return "LivingGlyph[ " + type + ", " + segments + "]";
    }
}

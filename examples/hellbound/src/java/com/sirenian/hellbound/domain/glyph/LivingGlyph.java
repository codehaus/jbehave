package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;
import com.sirenian.hellbound.util.ListenerSet;

public class LivingGlyph extends Glyph {
	
	public static final LivingGlyph NULL = new LivingGlyph(GlyphType.NULL, CollisionDetector.NULL, 0) {
        public void addListener(GlyphListener listener) {}
        public void addListeners(ListenerSet listeners) {}
    };
    
    private final CollisionDetector detector;
    private Segment root;
    private int rotation;

	public LivingGlyph( 
			GlyphType type,
			CollisionDetector detector,
			int centre) {
		
		super(type, type.getSegments(0).movedRight(centre));
		this.detector = detector;
		this.type = type;
        this.rotation = 0;
        this.root = new Segment(centre, 0);
		
	}
	
	public boolean requestMoveDown() {
		return attemptMoveTo(segments.movedDown(), rotation, new Segment(root.x, root.y + 1));
	}

    public boolean requestMoveLeft() {
        return attemptMoveTo(segments.movedLeft(), rotation, new Segment(root.x - 1, root.y));
    }

    public boolean requestMoveRight() {
        return attemptMoveTo(segments.movedRight(), rotation, new Segment(root.x + 1, root.y));
    }


    public boolean requestRotateLeft() {
        int newRotation = rebound(rotation + 1);
        return attemptMoveTo(type.getSegments(newRotation).movedRight(root.x).movedDown(root.y), newRotation, root);        
    }

    public boolean requestRotateRight() {
        int newRotation = rebound(rotation - 1);
        return attemptMoveTo(type.getSegments(newRotation).movedRight(root.x).movedDown(root.y), newRotation, root); 
        
    }

    private int rebound(int newRotation) {
        return newRotation < 0 ? newRotation + 4 : newRotation % 4;
    }   

    private boolean attemptMoveTo(Segments newSegments, int newRotations, Segment newRoot) {
        if (detector.collides(newSegments)) {
            return false;
        } else {
            moveTo(newSegments);
            rotation = newRotations;
            root = newRoot;
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
        clearListeners();
    }
    
    public String toString() {
        return "LivingGlyph[ " + type + ", " + segments + "]";
    }



}

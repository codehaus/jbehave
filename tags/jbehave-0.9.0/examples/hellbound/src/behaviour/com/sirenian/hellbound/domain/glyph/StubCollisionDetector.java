package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;

public class StubCollisionDetector  implements CollisionDetector {
    private Segments segments = Segments.EMPTY;
    private int floor = 100; // a falling glyph 'timeout'

    public StubCollisionDetector(Segments segments) {
        this.segments = segments;
    }

    public StubCollisionDetector(int floor) {
        this.floor = floor;
    }

    public boolean collides(Segments segments) {
        return segments.overlaps(this.segments) || segments.lowest() > floor;
    }
        
}

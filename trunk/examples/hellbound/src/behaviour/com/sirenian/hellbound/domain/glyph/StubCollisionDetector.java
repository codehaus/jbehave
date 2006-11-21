package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.engine.CollisionDetector;

public class StubCollisionDetector  implements CollisionDetector {
    private final Segments segments;

    public StubCollisionDetector(Segments segments) {
        this.segments = segments;
    }

    public boolean collides(Segments segments) {
        return segments.overlaps(this.segments) || segments.lowest() > 20; // ensures 'timeout'
    }
        
}

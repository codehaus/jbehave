package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;

public class VerifiableGlyphListener implements GlyphListener {
    private Segments recordedSegments;

    public void reportGlyphMovement(GlyphType type, Segments fromSegments, Segments toSegments) {
        this.recordedSegments = toSegments;
    }
    
    public Segments getSegments() {
        return recordedSegments;
    }
}

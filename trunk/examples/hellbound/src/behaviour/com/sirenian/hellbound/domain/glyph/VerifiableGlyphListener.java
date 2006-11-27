package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.Logger;

public class VerifiableGlyphListener implements GlyphListener {
    private Segments toSegments;
    private GlyphType type;
    private Segments fromSegments;

    public void reportGlyphMovement(GlyphType type, Segments fromSegments, Segments toSegments) {
        Logger.debug(this, "Glyph movement reported from " + fromSegments + " to " + toSegments + " with type " + type);
        this.fromSegments = fromSegments;
        this.toSegments = toSegments;
        this.type = type;
    }
    
    public Segments fromLastSegments() {
        return fromSegments;
    }
    
    public Segments toLastSegments() {
        return toSegments;
    }
    
    public GlyphType getLastType() {
        return type;
    }
}

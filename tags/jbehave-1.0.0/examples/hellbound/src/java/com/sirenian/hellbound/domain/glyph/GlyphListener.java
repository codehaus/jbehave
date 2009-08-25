package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.Listener;

public interface GlyphListener extends Listener {
	
	GlyphListener NULL = new GlyphListener() {
        public void reportGlyphMovement(GlyphType type, Segments origin, Segments destination) {}};

    void reportGlyphMovement(GlyphType type, Segments origin, Segments destination);
}
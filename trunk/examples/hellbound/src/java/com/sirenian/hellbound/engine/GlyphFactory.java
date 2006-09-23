package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Heartbeat;

public interface GlyphFactory {
    public Glyph nextGlyph(Heartbeat heartbeat, int center);
}

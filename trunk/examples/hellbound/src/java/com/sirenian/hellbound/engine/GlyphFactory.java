package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Glyph;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.util.ListenerSet;

public interface GlyphFactory {
    public Glyph nextGlyph(Heartbeat heartbeat, int center, ListenerSet glyphListeners);
}

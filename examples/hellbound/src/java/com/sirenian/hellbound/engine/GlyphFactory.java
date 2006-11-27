package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Junk;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.util.ListenerSet;

public interface GlyphFactory {
    public LivingGlyph nextGlyph(int center, CollisionDetector detector, ListenerSet glyphListeners);

	public Junk createJunk(ListenerSet glyphListeners);
}

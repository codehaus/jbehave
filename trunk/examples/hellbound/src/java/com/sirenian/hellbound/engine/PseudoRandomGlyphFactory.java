package com.sirenian.hellbound.engine;

import java.util.Random;

import com.sirenian.hellbound.domain.glyph.Glyph;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.util.ListenerSet;

public class PseudoRandomGlyphFactory implements GlyphFactory {

    private Random random;

    public PseudoRandomGlyphFactory() {
        this(System.currentTimeMillis());
    }
    
    public PseudoRandomGlyphFactory(long seed) {
        random = new Random(seed);
    }

    public Glyph nextGlyph(Heartbeat heartbeat, int center, ListenerSet glyphListeners) {
        LivingGlyph glyph = new LivingGlyph(heartbeat, GlyphType.ALL_LIVING[random.nextInt(GlyphType.ALL_LIVING.length)], center);
        glyph.addListeners(glyphListeners);
		return glyph;
    }

}

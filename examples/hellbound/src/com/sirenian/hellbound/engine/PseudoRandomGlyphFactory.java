package com.sirenian.hellbound.engine;

import java.util.Random;

import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;

public class PseudoRandomGlyphFactory implements GlyphFactory {

    private Random random;

    public PseudoRandomGlyphFactory() {
        this(System.currentTimeMillis());
    }
    
    public PseudoRandomGlyphFactory(long seed) {
        random = new Random(seed);
    }

    public Glyph nextGlyph(Heartbeat heartbeat, int center) {
        return new LivingGlyph(heartbeat, GlyphType.ALL_LIVING[random.nextInt(GlyphType.ALL_LIVING.length)], center);
    }

}

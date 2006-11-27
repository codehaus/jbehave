package com.sirenian.hellbound.engine;

import java.util.Random;

import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Junk;
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

    public LivingGlyph nextGlyph(int center, CollisionDetector detector, ListenerSet glyphListeners) {
        GlyphType glyphType = GlyphType.ALL_LIVING[random.nextInt(GlyphType.ALL_LIVING.length)];
        LivingGlyph glyph = new LivingGlyph(glyphType, detector, center);
        glyph.addListeners(glyphListeners);
		return glyph;
    }

	public Junk createJunk(ListenerSet glyphListeners) {
		Junk junk = new Junk();
        junk.addListeners(glyphListeners);
        return junk;
	}

}

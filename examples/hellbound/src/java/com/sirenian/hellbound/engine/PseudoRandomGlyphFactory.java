package com.sirenian.hellbound.engine;

import java.util.Random;

import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Junk;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.util.ListenerSet;

public class PseudoRandomGlyphFactory implements GlyphFactory {

    private final Random random;
    private final int width;
    private final int height;

    public PseudoRandomGlyphFactory(int width, int height) {
        this(System.currentTimeMillis(), width, height);
    }
    
    public PseudoRandomGlyphFactory(long seed, int width, int height) {
        this.width = width;
        this.height = height;
        random = new Random(seed);
    }

    public LivingGlyph nextGlyph(CollisionDetector detector, ListenerSet glyphListeners) {
        GlyphType glyphType = GlyphType.ALL_LIVING[random.nextInt(GlyphType.ALL_LIVING.length)];
        LivingGlyph glyph = new LivingGlyph(glyphType, detector, CenterCalculator.forWidth(width));
        glyph.addListeners(glyphListeners);
		return glyph;
    }

	public Junk createJunk(ListenerSet glyphListeners) {
		Junk junk = new Junk(width, this.height);
        junk.addListeners(glyphListeners);
        return junk;
	}

}

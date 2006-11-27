package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.ListenerSet;

public class Junk extends Glyph {

	public static final Junk NULL = new Junk() {
        public void absorb(LivingGlyph glyph) {}
        public void addListener(GlyphListener listener) {}
        public void addListeners(ListenerSet listeners) {}
    };

    public Junk() {
		super(GlyphType.JUNK, Segments.EMPTY);
	}

    public void absorb(LivingGlyph glyph) {
        moveTo(segments.add(glyph.getSegments()));
        glyph.kill();
    }
}

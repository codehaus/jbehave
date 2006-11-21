package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;

public class Junk extends Glyph {

	public Junk() {
		super(GlyphType.JUNK, Segments.EMPTY);
	}

	public void add(Segments segments) {
		moveTo(this.segments.add(segments));
	}
}

package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.ListenerSet;

public class Junk extends Glyph {

	public static final Junk NULL = new Junk(0, 0) {
        public void absorb(LivingGlyph glyph) {}
        public void addListener(GlyphListener listener) {}
        public void addListeners(ListenerSet listeners) {}
    };
    private final int width;
    private final int height;

    public Junk(int width, int height) {
		super(GlyphType.JUNK, Segments.EMPTY);
        this.width = width;
        this.height = height;
	}

    public void absorb(LivingGlyph glyph) {
        Segments newSegments = segments.add(glyph.getSegments());
        glyph.kill();
        newSegments = removeAnyFilledLines(newSegments);
        moveTo(newSegments);
    }

    private Segments removeAnyFilledLines(Segments newSegments) {
        Segments results = newSegments;
        for (int y = 0; y < height; y++) {
            boolean lineFilled = true;
            for (int x = 0; x < width && lineFilled; x++) {
                if (!results.contains(new Segment(x, y))) {
                    lineFilled = false;
                }
            }
            if (lineFilled) {
                results = removeLine(results, y);
            }
        }
        return results;
    }

    private Segments removeLine(Segments newSegments, int y) {
        Segments results = newSegments;
        for (int x = 0; x < width; x++) {
            results = results.remove(new Segment(x, y));
        }
        
        for (int j = y - 1; j >= 0; j--) {
            for (int x = 0; x < width; x++) {
                if (newSegments.contains(new Segment(x, j))) {
                    results = results.replace(new Segment(x, j), new Segment(x, j + 1));
                }
                
            }
        }
        return results;
    }
}

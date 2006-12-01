package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;

import jbehave.core.Ensure;
import jbehave.core.story.domain.World;

public class TheGlyphSegmentsShouldBecomeJunk extends HellboundOutcome {
    public void verifyAnyTimeIn(World world) {
        Segments expectedSegments = new Segments(
                new Segment(2, 11),
                new Segment(3, 11),
                new Segment(4, 11),
                new Segment(3, 12)
        );
        Color expectedColor = Hellbound.COLORMAP.getColorFor(GlyphType.JUNK);
        
        RenderedPit pit = getPit(world);
        Ensure.that(pit, contains(expectedSegments, expectedColor));
    }

}

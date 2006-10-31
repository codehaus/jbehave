package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import jbehave.core.Ensure;
import jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;

public class TheGlyphShouldMoveDownwards extends HellboundOutcome {

    private Segments expectedSegments;
	private Color expectedColor;
    
	public void setExpectationIn(World world) {
        expectedSegments = new Segments(
        		new Segment(2, 1),
        		new Segment(3, 1),
        		new Segment(4, 1),
        		new Segment(3, 2)
        );
        expectedColor = Hellbound.COLORMAP.getColorFor(GlyphType.T);
	}

	public void verifyAnyTimeIn(World world) {
        RenderedPit pit = getPit(world);
        Ensure.that(pit, contains(expectedSegments, expectedColor));
	}

}

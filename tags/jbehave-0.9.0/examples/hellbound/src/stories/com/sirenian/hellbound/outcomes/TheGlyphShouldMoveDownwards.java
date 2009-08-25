package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import org.jbehave.core.Ensure;
import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;

public class TheGlyphShouldMoveDownwards extends HellboundOutcome {
	public void verifyAnyTimeIn(World world) {
	    Segments expectedSegments = T_SHAPE_AT_TOP.movedDown();
	    Color expectedColor = Hellbound.COLORMAP.getColorFor(GlyphType.T);
        RenderedPit pit = getPit(world);
        Ensure.that(pit, contains(expectedSegments, expectedColor));
	}
}

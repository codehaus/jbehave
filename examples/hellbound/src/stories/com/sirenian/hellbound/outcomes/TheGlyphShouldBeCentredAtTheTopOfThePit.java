package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import org.jbehave.core.Ensure;
import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.stories.util.WorldKey;

public class TheGlyphShouldBeCentredAtTheTopOfThePit extends HellboundOutcome {
	public void verifyAnyTimeIn(World world) {
	    Segments expectedSegments = T_SHAPE_AT_TOP;
	    Color expectedColor = Hellbound.COLORMAP.getColorFor(GlyphType.T);
		RenderedPit graphics = (RenderedPit) world.get(WorldKey.PIT, null);
        Ensure.that(graphics, contains(expectedSegments, expectedColor));
	}
}

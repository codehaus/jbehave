package com.sirenian.hellbound.outcomes;

import jbehave.core.Ensure;
import jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.stories.WorldKey;

public class TheFirstGlyphShouldBeDisplayedInThePit extends
		HellboundOutcome {

	private Segments expectedSegments;

    public void setExpectationIn(World world) {
		GlyphType type = GlyphType.T;
		expectedSegments = type.nominalSegments(0).movedRight(3);
	}

	public void verifyAnyTimeIn(World world) {
		RenderedPit graphics = (RenderedPit) world.get(WorldKey.PIT, null);
        Ensure.that(graphics, contains(expectedSegments, Hellbound.COLORMAP.getColorFor(GlyphType.T)));
	}


}

package com.sirenian.hellbound.outcomes;

import jbehave.core.Ensure;
import jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.stories.WorldKey;

public class TheFirstGlyphShouldBeDisplayedInThePit extends
		HellboundOutcome {

	private Segment[] expectedSegments;

    public void setExpectationIn(World world) {
		GlyphType type = GlyphType.T;
		expectedSegments = new Segment[4];
        for (int i = 0; i < 4; i++) {
            expectedSegments[i] = type.rotationsAtRoot[0][i].movedRight(3);
        }
	}

	public void verify(World world) {
		RenderedPit graphics = (RenderedPit) world.get(WorldKey.PIT, null);
        Ensure.that(graphics, contains(expectedSegments, Hellbound.COLORMAP.getColorFor(GlyphType.T)));
	}

}

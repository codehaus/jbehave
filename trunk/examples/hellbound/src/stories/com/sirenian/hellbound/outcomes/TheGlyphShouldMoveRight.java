package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.stories.WorldKey;

public class TheGlyphShouldMoveRight extends HellboundOutcome {

    private Segments expectedSegments;
    private Color expectedColor;

    public void setExpectationIn(World world) {
        expectedSegments = T_SHAPE_AT_TOP.movedRight();
        expectedColor =  Hellbound.COLORMAP.getColorFor(GlyphType.T);
    }
    
    protected void verifyAnyTimeIn(World world) {
        Object pit = world.get(WorldKey.PIT);
        ensureThat(pit, contains(expectedSegments, expectedColor));
    }

}

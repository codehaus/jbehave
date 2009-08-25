package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.stories.util.WorldKey;

public class TheGlyphShouldMoveRight extends HellboundOutcome {
    protected void verifyAnyTimeIn(World world) {
        Segments expectedSegments = T_SHAPE_AT_TOP.movedRight();
        Color expectedColor =  Hellbound.COLORMAP.getColorFor(GlyphType.T);
        Object pit = world.get(WorldKey.PIT);
        ensureThat(pit, contains(expectedSegments, expectedColor));
    }
}

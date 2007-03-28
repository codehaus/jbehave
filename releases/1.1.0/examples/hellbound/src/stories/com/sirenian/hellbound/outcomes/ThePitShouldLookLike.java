package com.sirenian.hellbound.outcomes;

import org.jbehave.core.Ensure;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;

import com.sirenian.hellbound.gui.RenderedPit;

public class ThePitShouldLookLike extends HellboundOutcome {
    private final String expectedPit;

    public ThePitShouldLookLike(String expectedPit) {
        this.expectedPit = expectedPit;
    }

    public void verifyAnyTimeIn(World world) {
        RenderedPit pit = getPit(world);
        Ensure.that(pit, looksLike(expectedPit));
    }
    
    public void narrateTo(Renderer renderer) {
        super.narrateTo(renderer);
        renderer.renderAny(expectedPit);
    }
}

package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.Narrative;

import com.sirenian.hellbound.scenarios.TheGlyphWillNotMoveDown;
import com.sirenian.hellbound.scenarios.TheGlyphWillNotMoveLeft;
import com.sirenian.hellbound.scenarios.TheGlyphWillNotMoveRight;
import com.sirenian.hellbound.stories.util.HellboundStoryBase;

public class TheGlyphIsConstrainedByThePit extends HellboundStoryBase {

    public TheGlyphIsConstrainedByThePit() {
        super(new Narrative("game player", "the glyph to be constrained by the pit", "the game has boundaries"));
    }
    
    public void specify() {
        addScenario(new TheGlyphWillNotMoveRight());
        addScenario(new TheGlyphWillNotMoveLeft());
        addScenario(new TheGlyphWillNotMoveDown());
    }

}

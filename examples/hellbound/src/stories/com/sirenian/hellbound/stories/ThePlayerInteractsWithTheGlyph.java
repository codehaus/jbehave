package com.sirenian.hellbound.stories;

import com.sirenian.hellbound.scenarios.ThePlayerMovesTheGlyph;
import com.sirenian.hellbound.scenarios.ThePlayerRotatesTheGlyphLeft;
import com.sirenian.hellbound.scenarios.ThePlayerRotatesTheGlyphRight;

import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;

public class ThePlayerInteractsWithTheGlyph extends ScenarioDrivenStory {

    public ThePlayerInteractsWithTheGlyph() {
        super(new Narrative("game player", "to move the shape", "I can make space for the next glyph"));
        
        addScenario(new ThePlayerMovesTheGlyph());
        addScenario(new ThePlayerRotatesTheGlyphLeft());
        addScenario(new ThePlayerRotatesTheGlyphRight());
    }
}

package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;

import com.sirenian.hellbound.scenarios.ThePlayerMovesTheGlyph;
import com.sirenian.hellbound.scenarios.ThePlayerRotatesTheGlyphLeft;
import com.sirenian.hellbound.scenarios.ThePlayerRotatesTheGlyphRight;


public class ThePlayerInteractsWithTheGlyph extends ScenarioDrivenStory {

    public ThePlayerInteractsWithTheGlyph() {
        super(new Narrative("game player", "to move the shape", "I can make space for the next glyph"));
    }

    public void specify() {
        addScenario(new ThePlayerMovesTheGlyph());
        addScenario(new ThePlayerRotatesTheGlyphLeft());
        addScenario(new ThePlayerRotatesTheGlyphRight());
    }
}

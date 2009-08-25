package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;

import com.sirenian.hellbound.scenarios.ThePlayerDropsTheGlyphIntoAnEmptyPit;
import com.sirenian.hellbound.scenarios.ThePlayerDropsTheGlyphOntoJunk;


public class ThePlayerDropsTheGlyph extends ScenarioDrivenStory {

	public ThePlayerDropsTheGlyph() {
		super(new Narrative("game player", "to drop the glyph", " I can save time"));
	}

    public void specify() {
        addScenario(new ThePlayerDropsTheGlyphIntoAnEmptyPit());
        addScenario(new ThePlayerDropsTheGlyphOntoJunk());
    }
}

package com.sirenian.hellbound.stories;

import com.sirenian.hellbound.scenarios.ThePlayerDropsTheGlyphIntoAnEmptyPit;
import com.sirenian.hellbound.scenarios.ThePlayerDropsTheGlyphOntoJunk;

import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;

public class ThePlayerDropsTheGlyph extends ScenarioDrivenStory {

	public ThePlayerDropsTheGlyph() {
		super(new Narrative("game player", "to drop the glyph", " I can save time"));
		
		addScenario(new ThePlayerDropsTheGlyphIntoAnEmptyPit());
        addScenario(new ThePlayerDropsTheGlyphOntoJunk());
	}

}

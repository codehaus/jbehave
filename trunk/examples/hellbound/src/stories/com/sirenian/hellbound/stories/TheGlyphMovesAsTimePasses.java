package com.sirenian.hellbound.stories;

import jbehave.core.story.domain.Narrative;

import com.sirenian.hellbound.scenarios.ThePlayerSeesTheFirstGlyphMove;

public class TheGlyphMovesAsTimePasses extends HellboundStory {

	public TheGlyphMovesAsTimePasses() {
		super(new Narrative("game player", "the glyph to move downwards as time passes", "the game is a challenge"));
		
		addScenario(new ThePlayerSeesTheFirstGlyphMove());
	}
	
}

package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;

public class ThePlayerSeesTheFirstGlyphMove extends ScenarioUsingMiniMock {
	
	public ThePlayerSeesTheFirstGlyphMove() {
		super("The player sees the first glyph move", 
				"The glyph moves as time passes",
				given(new TheFirstGlyphIsDisplayedOnTheBoard()),
				when(new TimePasses()), 
                then(new TheGlyphShouldMoveDownwards()));
	}



}

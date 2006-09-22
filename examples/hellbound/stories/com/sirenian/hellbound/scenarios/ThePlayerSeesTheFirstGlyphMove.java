package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.givens.HellboundIsRunning;
import com.sirenian.hellbound.events.ThePlayerStartsTheGame;
import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheFirstGlyphShouldBeDisplayedInThePit;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;

public class ThePlayerSeesTheFirstGlyphMove extends ScenarioUsingMiniMock {
	
	public ThePlayerSeesTheFirstGlyphMove() {
		super("The player sees the first glyph move", 
				"The glyph moves as time passes",
				new HellboundIsRunning(),
                new ThePlayerStartsTheGame(), 
                new TheFirstGlyphShouldBeDisplayedInThePit(),
				new TimePasses(), 
                new TheGlyphShouldMoveDownwards());
	}
}

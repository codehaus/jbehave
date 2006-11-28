package com.sirenian.hellbound.scenarios;

import jbehave.core.story.domain.ScenarioUsingMiniMock;

import com.sirenian.hellbound.events.ThePlayerStartsTheGame;
import com.sirenian.hellbound.givens.HellboundIsRunning;
import com.sirenian.hellbound.outcomes.TheFirstGlyphShouldBeDisplayedInThePit;

public class TheFirstGlyphIsDisplayedOnTheBoard extends ScenarioUsingMiniMock {

	public TheFirstGlyphIsDisplayedOnTheBoard() {
		super(given(new HellboundIsRunning()),
                when(new ThePlayerStartsTheGame()),
			then(new TheFirstGlyphShouldBeDisplayedInThePit())				
		);
	}


}

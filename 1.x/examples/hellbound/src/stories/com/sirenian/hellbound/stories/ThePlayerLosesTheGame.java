package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.Narrative;

import com.sirenian.hellbound.scenarios.ThereIsNoSpaceForTheNextGlyph;
import com.sirenian.hellbound.stories.util.HellboundStoryBase;

public class ThePlayerLosesTheGame extends HellboundStoryBase{
    
    public ThePlayerLosesTheGame() {
        super(new Narrative("game player", "to lose the game", "the game is a challenge"));
    }

    public void specify() {
        addScenario(new ThereIsNoSpaceForTheNextGlyph());
    }
}

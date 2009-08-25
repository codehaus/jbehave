package com.sirenian.hellbound.stories;

import org.jbehave.core.story.domain.Narrative;

import com.sirenian.hellbound.scenarios.ThePlayerDropsTheGlyphToMakeALine;
import com.sirenian.hellbound.stories.util.HellboundStoryBase;

public class ThePlayerMakesALine extends HellboundStoryBase {
    
    
    public ThePlayerMakesALine() {
        super(new Narrative("game player", "lines to disappear when I complete them", "I can progress the game"));
    }

    public void specify() {
        addScenario(new ThePlayerDropsTheGlyphToMakeALine());
    }
}

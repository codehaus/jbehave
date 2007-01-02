package com.sirenian.hellbound.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import com.sirenian.hellbound.events.TimePasses;
import com.sirenian.hellbound.outcomes.TheGlyphShouldMoveDownwards;

public class ThePlayerSeesTheFirstGlyphMove extends MultiStepScenario {

    public void specifySteps() {
        given(new TheFirstGlyphIsDisplayedOnTheBoard());
        when(new TimePasses());
        then(new TheGlyphShouldMoveDownwards());
    }

}

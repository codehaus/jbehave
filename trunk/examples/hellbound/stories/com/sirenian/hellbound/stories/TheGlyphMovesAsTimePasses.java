package com.sirenian.hellbound.stories;

import jbehave.core.behaviour.Behaviour;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.result.Result;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;
import jbehave.extensions.threaded.swing.WindowWrapper;
import jbehave.extensions.threaded.time.TimeoutException;

import com.sirenian.hellbound.scenarios.ThePlayerSeesTheFirstGlyphMove;

public class TheGlyphMovesAsTimePasses extends ScenarioDrivenStory {

	public TheGlyphMovesAsTimePasses() {
		super(new Narrative("game player", "the glyph to move downwards as time passes", "the game is a challenge"));
		
		addScenario(new ThePlayerSeesTheFirstGlyphMove());
        
        addListener(new BehaviourListener() {
            private WindowWrapper wrapper = new DefaultWindowWrapper("HellboundFrame");;

            public void gotResult(Result result) {
                try {
                    wrapper.closeWindow();
                } catch (TimeoutException e) {}
            }

            public void before(Behaviour behaviour) {}
            public void after(Behaviour behaviour) {}
            
        });
	}
	
}

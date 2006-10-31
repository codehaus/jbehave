package com.sirenian.hellbound.stories;

import com.sirenian.hellbound.gui.ComponentNames;

import jbehave.core.behaviour.Behaviour;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.result.Result;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;
import jbehave.extensions.threaded.swing.WindowWrapper;
import jbehave.extensions.threaded.time.TimeoutException;

public class HellboundStory extends ScenarioDrivenStory {

	public HellboundStory(Narrative narrative) {
		super(narrative);
		
        addListener(new BehaviourListener() {
            private WindowWrapper wrapper = new DefaultWindowWrapper(ComponentNames.HELLBOUND_FRAME);

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

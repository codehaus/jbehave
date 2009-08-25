package org.jbehave.it.stories.stories;

import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;

public class UserWantsToDoSomething extends ScenarioDrivenStory {

    public UserWantsToDoSomething() {
        super(new Narrative("User wants to do something", "", ""));
    }
    
    public void specify() {
        	      addScenario(new org.jbehave.it.stories.scenarios.UserIsAbleToDoIt());
    	    }

}
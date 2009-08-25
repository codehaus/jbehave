package org.jbehave.it.stories.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

public class UserIsAbleToDoIt extends MultiStepScenario {

    public void specifySteps() {
    	when(new org.jbehave.it.stories.events.UserExecutesCommand());
     		      given(new org.jbehave.it.stories.givens.SomethingToBeDone());
    	     		      then(new org.jbehave.it.stories.outcomes.CommandIsExecutedSuccessfully());
    		}    

}

/*
 * Created on 16-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.invoker.ScenarioInvoker;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifierBehaviour extends UsingMiniMock {
    
    public void shouldVerifyAllScenariosInAStory() throws Exception {
        // given...
        Mock scenario1 = mock(Scenario.class, "scenario1");
        Mock scenario2 = mock(Scenario.class, "scenario2");
        AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
        acceptanceCriteria.add((Scenario) scenario1);
        acceptanceCriteria.add((Scenario) scenario2);
        Story story = new Story(new Narrative("","",""), acceptanceCriteria);
        
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        StoryVerifier verifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker);
        
        // expect...
        scenarioInvoker.expects("invoke").with(scenario1).id("1");
        scenarioInvoker.expects("invoke").with(scenario2).after("1");
        
        // when...
        verifier.verify(story);
        
        // verify...
        verifyMocks();
    }
}

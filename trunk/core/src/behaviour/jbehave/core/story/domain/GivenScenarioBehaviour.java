/*
 * Created on 22-12-2004
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class GivenScenarioBehaviour extends UsingMiniMock {
	
	public void shouldRunScenarioWithWorldWhenSetUp() throws Exception {
		Mock scenario = mock(Scenario.class);
        World world = (World) stub(World.class);
        scenario.expects("run").with(world);
                
        GivenScenario givenScenario = new GivenScenario((Scenario) scenario);
        givenScenario.setUp(world);
	}
}

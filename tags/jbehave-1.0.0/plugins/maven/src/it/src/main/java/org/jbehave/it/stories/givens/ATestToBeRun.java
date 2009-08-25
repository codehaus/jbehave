package org.jbehave.it.stories.givens;

import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.story.domain.World;

public class ATestToBeRun extends GivenUsingMiniMock {

    public void setUp(World world) {
        world.put("TestKey", "TestValue");
        System.out.println("Given "+ATestToBeRun.class.getName());        
    }

}

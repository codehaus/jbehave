package org.jbehave.it.stories.events;

import org.jbehave.core.minimock.story.domain.EventUsingMiniMock;
import org.jbehave.core.story.domain.World;

public class UserRunsTest extends EventUsingMiniMock {

    public void occurIn(World world) {
        world.put("TestKey", "AnotherValue");
        System.out.println("When "+UserRunsTest.class.getName());
    }

}

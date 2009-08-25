package org.jbehave.it.stories.outcomes;

import org.jbehave.core.minimock.story.domain.OutcomeUsingMiniMock;
import org.jbehave.core.story.domain.World;

public class ATestIsRunSuccessfully extends OutcomeUsingMiniMock {

    public void verify(World world) {
        ensureThat(world.get("TestKey"), isNotNull());
        System.out.println("Then "+ATestIsRunSuccessfully.class.getName());
    }

}

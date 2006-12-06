package com.sirenian.hellbound.givens;

import com.sirenian.hellbound.stories.Idler;
import com.sirenian.hellbound.util.Logger;

import jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;

public abstract class HellboundGiven extends GivenUsingMiniMock {

	private Idler idler;

	public HellboundGiven() {
		idler = new Idler();
	}

	
	public void setUp(World world) {
        Logger.debug(this, "setting up Given");
		setUpAnyTimeIn(world);
		idler.waitForIdle();
	}
	
	public abstract void setUpAnyTimeIn(World world);

}

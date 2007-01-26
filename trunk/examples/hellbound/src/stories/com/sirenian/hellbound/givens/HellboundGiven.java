package com.sirenian.hellbound.givens;

import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.stories.util.Idler;
import com.sirenian.hellbound.util.Logger;


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

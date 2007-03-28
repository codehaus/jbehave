package com.sirenian.hellbound.events;

import org.jbehave.core.story.domain.World;

import com.sirenian.hellbound.gui.GameKeys;

public class ThePlayerPressesTheDropKey extends HellboundEvent {

	protected void occurAnyTimeIn(World world) {
		pressKey(GameKeys.DROP_GLYPH, world);
	}

}

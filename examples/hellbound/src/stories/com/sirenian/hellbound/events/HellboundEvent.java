package com.sirenian.hellbound.events;

import jbehave.core.story.domain.EventUsingMiniMock;
import jbehave.core.story.domain.World;
import jbehave.extensions.threaded.swing.SwingBehaviourException;
import jbehave.extensions.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.stories.Idler;
import com.sirenian.hellbound.stories.WorldKey;
import com.sirenian.hellbound.util.Logger;

public abstract class HellboundEvent extends EventUsingMiniMock {
	
	private Idler idler;

	public HellboundEvent() {
		idler = new Idler();
	}

    public void occurIn(World world) {
        Logger.debug(this, "performing Event");
        occurAnyTimeIn(world);
        idler.waitForIdle(world);
    }

    protected abstract void occurAnyTimeIn(World world);

	protected void clickButton(String buttonName, World world) {
		WindowWrapper wrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER, null);
	    try {
			wrapper.clickButton(buttonName);
	    } catch (SwingBehaviourException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	protected void pressKey(int keycode, World world) {
		WindowWrapper wrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER, null);
		try {
			wrapper.pressKeycode(keycode);
		} catch (SwingBehaviourException e) {
			throw new RuntimeException(e);
		}
	}

}

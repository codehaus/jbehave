package com.sirenian.hellbound.events;

import org.jbehave.core.minimock.story.domain.EventUsingMiniMock;
import org.jbehave.core.story.domain.World;
import org.jbehave.threaded.swing.SwingBehaviourException;
import org.jbehave.threaded.swing.WindowWrapper;


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

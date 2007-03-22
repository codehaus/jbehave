package com.sirenian.hellbound.events;

import org.jbehave.core.minimock.story.domain.EventUsingMiniMock;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.threaded.TimeoutException;
import org.jbehave.threaded.swing.ComponentFinderException;
import org.jbehave.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.stories.util.Idler;
import com.sirenian.hellbound.stories.util.WorldKey;
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
	    } catch (ComponentFinderException e) {
	        throw new RuntimeException(e);
	    } catch (TimeoutException e) {
	        throw new RuntimeException(e);
        }
	}
	
	protected void pressKey(int keycode, World world) {
		WindowWrapper wrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER, null);
		try {
			wrapper.pressKeycode(keycode);
		} catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
	}
    
    protected void pressKey(char keychar, World world) {
        WindowWrapper wrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER, null);
        try {
            wrapper.pressKeychar(keychar);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}

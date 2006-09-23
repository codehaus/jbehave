package com.sirenian.hellbound.events;

import jbehave.core.story.domain.World;
import jbehave.extensions.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.gui.ComponentNames;
import com.sirenian.hellbound.stories.WorldKey;

public class ThePlayerStartsTheGame extends HellboundEvent {

    protected void occurAnyTimeIn(World world) {
        
        WindowWrapper wrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER, null);
        
        try {
            wrapper.clickButton(ComponentNames.START_GAME_BUTTON);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

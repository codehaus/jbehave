package com.sirenian.hellbound.scenarios;

import javax.swing.JLabel;

import org.jbehave.core.Ensure;
import org.jbehave.core.story.domain.World;
import org.jbehave.threaded.swing.WindowWrapper;

import com.sirenian.hellbound.gui.ComponentNames;
import com.sirenian.hellbound.outcomes.HellboundOutcome;
import com.sirenian.hellbound.stories.util.WorldKey;

public class TheGameShouldBeOver extends HellboundOutcome {

    protected void verifyAnyTimeIn(World world) {
        WindowWrapper windowWrapper = (WindowWrapper) world.get(WorldKey.WINDOW_WRAPPER);
        JLabel messageLabel = null;
        try {
             messageLabel = (JLabel) windowWrapper.findComponent(ComponentNames.GAME_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Ensure.that(messageLabel.getText(), eq("Game over, man! Game over!"));
    }

}

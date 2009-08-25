package com.sirenian.hellbound.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.glyph.GlyphMovement;
import com.sirenian.hellbound.util.Logger;

public class ActionFactory {

    public Action createAction(final GameRequestListener gameRequestListener, final GlyphMovement movement) {
        Logger.debug(this, "Creating " + movement + " action ");
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestGlyphMovement(movement);
            }
        };
    }    

}

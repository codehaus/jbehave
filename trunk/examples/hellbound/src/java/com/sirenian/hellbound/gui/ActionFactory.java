package com.sirenian.hellbound.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.util.Logger;

public class ActionFactory {

    public Action right(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Logger.debug(this, "Adding right action to frame action map");
                gameRequestListener.requestMoveGlyphRight();
            }
        };
    }
    
    public Action left(final GameRequestListener gameRequestListener) {
        Logger.debug(this, "Adding left action to frame action map");
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestMoveGlyphLeft();
            }
        };
    }

    public Action down(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Logger.debug(this, "Adding down action to frame action map");
                gameRequestListener.requestMoveGlyphDown();
            }
        };
    }
    
    public Action drop(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Logger.debug(this, "Adding drop action to frame action map");
                gameRequestListener.requestDropGlyph();
            }
        };
    }    

}

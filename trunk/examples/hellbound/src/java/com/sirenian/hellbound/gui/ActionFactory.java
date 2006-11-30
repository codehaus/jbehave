package com.sirenian.hellbound.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ActionFactory {

    public Action right(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestMoveGlyphRight();
            }
        };
    }
    
    public Action left(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestMoveGlyphLeft();
            }
        };
    }

    public Action down(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestMoveGlyphDown();
            }
        };
    }
    
    public Action drop(final GameRequestListener gameRequestListener) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameRequestListener.requestDropGlyph();
            }
        };
    }    

}

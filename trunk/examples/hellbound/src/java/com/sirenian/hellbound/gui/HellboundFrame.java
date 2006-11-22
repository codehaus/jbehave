package com.sirenian.hellbound.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.util.Logger;

public class HellboundFrame extends JFrame implements GameListener {
	
	private static final long serialVersionUID = 1093822705535094763L;
	
	private CardLayout cardLayout;

    private JPanel contentPanel;

	public HellboundFrame(JPanel readyPanel, JPanel runningPanel) {

        setName(ComponentNames.HELLBOUND_FRAME);
        
        contentPanel = new JPanel();
        setContentPane(contentPanel);
        
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        
        contentPanel.add(readyPanel, GameState.READY.toString());
        contentPanel.add(runningPanel, GameState.RUNNING.toString());
        
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "dropGlyph");
        
        this.pack();
        this.setVisible(true);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

    public void setGameRequestListener(final GameRequestListener gameRequestListener) {
        contentPanel.getActionMap().put("dropGlyph", new AbstractAction() {
            private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) {
                Logger.debug(this, "Space key detected");
                gameRequestListener.requestDropGlyph();
            }
        });
    }

	public void reportGameStateChanged(GameState state) {
		Logger.debug(this, "gameStateChanged to " + state);
		cardLayout.show(this.getContentPane(), state.toString());
	}
}

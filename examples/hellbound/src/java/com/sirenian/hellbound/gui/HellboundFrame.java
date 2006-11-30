package com.sirenian.hellbound.gui;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.util.Logger;

public class HellboundFrame extends JFrame implements GameListener {
	
	private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel();
    private ActionFactory actionFactory = new ActionFactory();

	public HellboundFrame(JPanel readyPanel, JPanel runningPanel) {

        setName(ComponentNames.HELLBOUND_FRAME);
        
        setContentPane(contentPanel);
        
        contentPanel.setLayout(cardLayout);
        
        contentPanel.add(readyPanel, GameState.READY.toString());
        contentPanel.add(runningPanel, GameState.RUNNING.toString());
        
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "drop");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        
        this.pack();
        this.setVisible(true);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

    public void setGameRequestListener(final GameRequestListener gameRequestListener) {
        contentPanel.getActionMap().put("drop", actionFactory.drop(gameRequestListener));
        contentPanel.getActionMap().put("left", actionFactory.left(gameRequestListener));
        contentPanel.getActionMap().put("right", actionFactory.right(gameRequestListener));
        contentPanel.getActionMap().put("down", actionFactory.down(gameRequestListener));
    }

	public void reportGameStateChanged(GameState state) {
		Logger.debug(this, "gameStateChanged to " + state);
		cardLayout.show(this.getContentPane(), state.toString());
	}
}

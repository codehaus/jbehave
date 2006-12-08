package com.sirenian.hellbound.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.GlyphMovement;
import com.sirenian.hellbound.util.Logger;

public class HellboundFrame extends JFrame implements GameListener {
	
	private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private JLabel messageLabel = new JLabel();
    private ActionFactory actionFactory = new ActionFactory();

	public HellboundFrame(JPanel readyPanel, JPanel pitPanel) {

        setName(ComponentNames.HELLBOUND_FRAME);
        messageLabel.setName(ComponentNames.GAME_MESSAGE);
        messageLabel.setPreferredSize(new Dimension((int)pitPanel.getPreferredSize().getWidth(), 100));

        contentPanel.setName("Hellbound frame content panel");
        
        setContentPane(contentPanel);
        
        contentPanel.add(readyPanel, GameState.READY.toString());
        contentPanel.add(createRunningPanel(pitPanel), GameState.RUNNING.toString());
        
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(' '), "drop");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke('z'), "leftRotate");
        contentPanel.getInputMap().put(KeyStroke.getKeyStroke('x'), "rightRotate");
        
        this.pack();
        this.setVisible(true);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

    private JPanel createRunningPanel(JPanel pitPanel) {
        JPanel runningPanel = new JPanel();
        runningPanel.setName("running.panel");
        runningPanel.setLayout(new BorderLayout());
        runningPanel.add(pitPanel, BorderLayout.CENTER);
        runningPanel.add(messageLabel, BorderLayout.NORTH);
        return runningPanel;
    }

    public void setGameRequestListener(final GameRequestListener gameRequestListener) {
        contentPanel.getActionMap().clear();
        contentPanel.getActionMap().put("drop", actionFactory.createAction(gameRequestListener, GlyphMovement.DROP));
        contentPanel.getActionMap().put("left", actionFactory.createAction(gameRequestListener, GlyphMovement.LEFT));
        contentPanel.getActionMap().put("right", actionFactory.createAction(gameRequestListener, GlyphMovement.RIGHT));
        contentPanel.getActionMap().put("down", actionFactory.createAction(gameRequestListener, GlyphMovement.DOWN));
        contentPanel.getActionMap().put("leftRotate", actionFactory.createAction(gameRequestListener, GlyphMovement.ROTATE_LEFT));
        contentPanel.getActionMap().put("rightRotate", actionFactory.createAction(gameRequestListener, GlyphMovement.ROTATE_RIGHT));
    }

	public void reportGameStateChanged(GameState state) {
		Logger.debug(this, "gameStateChanged to " + state);
        if (state == GameState.OVER) {
            messageLabel.setText("Game over, man! Game over!");
        } else {
            messageLabel.setText("");
            cardLayout.show(this.getContentPane(), state.toString());
        }
	}
}

package com.sirenian.hellbound.gui;

import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.util.Logger;


public class HellboundFrame extends JFrame implements GameListener {
	
	private static final long serialVersionUID = 1093822705535094763L;
	
	private CardLayout cardLayout;

	public HellboundFrame(JPanel readyPanel, JPanel runningPanel) {
        
        setUpSelf();		
		setUpContent(readyPanel, runningPanel);
	}

    public void setGameRequestListener(final GameRequestListener gameRequestListener) {
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                gameRequestListener.requestDropGlyph();
            }
        });
    }

    private void setUpContent(JPanel readyPanel, JPanel runningPanel) {
        cardLayout = new CardLayout();
		this.getContentPane().setLayout(cardLayout);
		this.getContentPane().add(readyPanel, GameState.READY.toString());
		this.getContentPane().add(runningPanel, GameState.RUNNING.toString());
		this.setVisible(true);
    }

    private void setUpSelf() {
        this.setName(ComponentNames.HELLBOUND_FRAME);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	public void reportGameStateChanged(GameState state) {
		Logger.debug(this, "gameStateChanged to " + state);
		cardLayout.show(this.getContentPane(), state.toString());
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		Logger.debug(this, "setting to " + (visible ? "visible" : "invisible"));
	}

}

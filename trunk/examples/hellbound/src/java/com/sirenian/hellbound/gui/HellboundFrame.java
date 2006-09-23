package com.sirenian.hellbound.gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;


public class HellboundFrame extends JFrame implements GameListener {
	
	private static final long serialVersionUID = 1093822705535094763L;
	
	private CardLayout cardLayout;

	public HellboundFrame(JPanel readyPanel, JPanel runningPanel) {
		this.setName(ComponentNames.HELLBOUND_FRAME);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		
		cardLayout = new CardLayout();
		this.getContentPane().setLayout(cardLayout);
		this.getContentPane().add(readyPanel, GameState.READY.toString());
		this.getContentPane().add(runningPanel, GameState.RUNNING.toString());
		this.setVisible(true);
	}

	public void reportGameStateChanged(GameState state) {
		cardLayout.show(this.getContentPane(), state.toString());
	}

}

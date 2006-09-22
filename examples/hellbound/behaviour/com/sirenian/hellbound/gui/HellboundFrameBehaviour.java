package com.sirenian.hellbound.gui;

import javax.swing.JPanel;

import jbehave.core.minimock.UsingMiniMock;

import com.sirenian.hellbound.domain.game.GameState;

public class HellboundFrameBehaviour extends UsingMiniMock {

	private JPanel frontPanel;
	private JPanel gamePanel;
	private HellboundFrame frame;
	
	public void setUp() {
		frontPanel = new JPanel();
		gamePanel = new JPanel();
		frame = new HellboundFrame(frontPanel, gamePanel);
	}
	
	public void tearDown() {
		frame.dispose();		
	}
	
	public void shouldDisplayTheFrontPanelWhenTheGameIsReady() {
		frame.reportGameStateChanged(GameState.READY);		
		ensureThat(frontPanel.isShowing());
		ensureThat(!gamePanel.isShowing());
	}
	
	public void shouldDisplayGamePanelWhenTheGameIsRunning() {
		frame.reportGameStateChanged(GameState.RUNNING);
		ensureThat(!frontPanel.isShowing());
		ensureThat(gamePanel.isShowing());
	}
}

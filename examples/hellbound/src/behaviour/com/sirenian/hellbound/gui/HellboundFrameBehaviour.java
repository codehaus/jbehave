package com.sirenian.hellbound.gui;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;

public class HellboundFrameBehaviour extends UsingMiniMock {

	private JPanel frontPanel;
	private JPanel gamePanel;
	private HellboundFrame frame;
    private Mock gameRequestListener;
    private DefaultWindowWrapper windowWrapper;
	
	public void setUp() {
        windowWrapper = new DefaultWindowWrapper(ComponentNames.HELLBOUND_FRAME);
		frontPanel = new JPanel();
		gamePanel = new JPanel();
        gameRequestListener = mock(GameRequestListener.class);
		frame = new HellboundFrame(frontPanel, gamePanel);
        frame.setGameRequestListener((GameRequestListener) gameRequestListener);
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
    
    public void shouldRequestThatTheShapeIsDroppedWhenTheSpaceKeyIsPressed() throws Exception {
        gameRequestListener.expects("dropShape").once();
        windowWrapper.pressKey(KeyEvent.VK_SPACE);
        verifyMocks();
    }
}

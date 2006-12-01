package com.sirenian.hellbound.gui;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;
import jbehave.extensions.threaded.time.TimeoutException;

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
		try {
            windowWrapper.closeWindow();
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }	
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
        ensureThatKeycodeProducesRequest(' ', "requestDropGlyph");
    }
    
    public void shouldRequestThatTheShapeIsMovedRightWhenTheRightKeyIsPressed() throws Exception {
        ensureThatKeycodeProducesRequest(KeyEvent.VK_RIGHT, "requestMoveGlyphRight");
    }
    
    public void shouldRequestThatTheShapeIsMovedLeftWhenTheMoveLeftKeyIsPressed() throws Exception {
        ensureThatKeycodeProducesRequest(KeyEvent.VK_LEFT, "requestMoveGlyphLeft");
    }
    
    public void shouldRequestThatTheShapeIsMovedDownWhenTheMoveDownKeyIsPressed() throws Exception {
        ensureThatKeycodeProducesRequest(KeyEvent.VK_DOWN, "requestMoveGlyphDown");
    }
    
    public void shouldRequestThatTheShapeIsRotatedLeftWhenTheZKeyIsPressed() throws Exception {
        ensureThatKeycodeProducesRequest('z', "requestRotateGlyphLeft");
    }
    
    public void shouldRequestThatTheShapeIsRotatedRightWhenTheXKeyIsPressed() throws Exception {
        ensureThatKeycodeProducesRequest('x', "requestRotateGlyphRight");

    }

    private void ensureThatKeycodeProducesRequest(int keycode, String expectedRequest) throws TimeoutException {
        setUp();
        try {
            gameRequestListener.expects(expectedRequest).once();
            
            frame.reportGameStateChanged(GameState.RUNNING);
            windowWrapper.pressKeycode(keycode);
            verifyMocks();
        } finally {
            tearDown();
        }
    }
    
    private void ensureThatKeycodeProducesRequest(char keyChar, String expectedRequest) throws TimeoutException {
        setUp();
        try {
            gameRequestListener.expects(expectedRequest).once();
            
            frame.reportGameStateChanged(GameState.RUNNING);
            windowWrapper.pressKeychar(keyChar);
            verifyMocks();
        } finally {
            tearDown();
        }
    }
}

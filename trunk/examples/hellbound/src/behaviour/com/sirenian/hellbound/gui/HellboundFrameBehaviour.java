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
        setUp();
		frame.reportGameStateChanged(GameState.READY);		
		ensureThat(frontPanel.isShowing());
		ensureThat(!gamePanel.isShowing());
        tearDown();
	}
	
	public void shouldDisplayGamePanelWhenTheGameIsRunning() {
        setUp();
		frame.reportGameStateChanged(GameState.RUNNING);
		ensureThat(!frontPanel.isShowing());
		ensureThat(gamePanel.isShowing());
        tearDown();
	}
    
    public void shouldRequestThatTheShapeIsDroppedWhenTheSpaceKeyIsPressed() throws Exception {
        setUp();
        ensureThatKeycodeProducesRequest(KeyEvent.VK_SPACE, "requestDropGlyph");
        tearDown();
    }
    
    public void shouldRequestThatTheShapeIsMovedRightWhenTheRightKeyIsPressed() throws Exception {
        setUp();
        ensureThatKeycodeProducesRequest(KeyEvent.VK_RIGHT, "requestMoveGlyphRight");
        tearDown();
    }
    
    public void shouldRequestThatTheShapeIsMovedLeftWhenTheMoveLeftKeyIsPressed() throws Exception {
        setUp();
        ensureThatKeycodeProducesRequest(KeyEvent.VK_LEFT, "requestMoveGlyphLeft");
        tearDown();
    }
    
    public void shouldRequestThatTheShapeIsMovedDownWhenTheMoveDownKeyIsPressed() throws Exception {
        setUp();
        ensureThatKeycodeProducesRequest(KeyEvent.VK_DOWN, "requestMoveGlyphDown");
        tearDown();
    }

    private void ensureThatKeycodeProducesRequest(int keycode, String expectedRequest) throws TimeoutException {
        gameRequestListener.expects(expectedRequest).once();
        
        frame.reportGameStateChanged(GameState.RUNNING);
        windowWrapper.pressKey(keycode);
        verifyMocks();
    }
}

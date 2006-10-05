package com.sirenian.hellbound.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;
import jbehave.extensions.threaded.swing.WindowWrapper;

public class FrontPanelBehaviour extends UsingMiniMock {
	
	public void shouldContainTheButtonToStartTheGame() throws Exception {

		WindowWrapper wrapper = new DefaultWindowWrapper("TestFrame");
		
		Mock gameStarter = mock(GameRequestListener.class);
		gameStarter.expects("requestStart");
		
		FrontPanel panel = new FrontPanel((GameRequestListener)gameStarter);

		JFrame frame = new JFrame();
		frame.setName("TestFrame");
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
		wrapper.clickButton("startGame.button");		
		verifyMocks();
		
		JButton button = (JButton)wrapper.findComponent("startGame.button");
		Ensure.that("Start Game", eq(button.getText()));
		
		frame.dispose();
	}
}

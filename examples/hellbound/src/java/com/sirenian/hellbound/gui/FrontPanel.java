package com.sirenian.hellbound.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class FrontPanel extends JPanel {

	private static final long serialVersionUID = 8698430563589309679L;

	public FrontPanel(final GameRequestListener starter) {
		JButton button = new JButton("Start Game");
		button.setName("startGame.button");
		add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				starter.requestGameStart();
			}			
		});
	}

}

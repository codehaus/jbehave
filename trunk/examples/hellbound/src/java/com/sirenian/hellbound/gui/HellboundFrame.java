package com.sirenian.hellbound.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.util.Logger;


public class HellboundFrame extends JFrame implements GameListener {
	
	private static final long serialVersionUID = 1093822705535094763L;
	
	private CardLayout cardLayout;

    private final JPanel runningPanel;

	public HellboundFrame(JPanel readyPanel, JPanel runningPanel) {
        
        this.runningPanel = runningPanel;
        setUpSelf();		
		setUpContent(readyPanel, runningPanel);
	}

    public void setGameRequestListener(final GameRequestListener gameRequestListener) {
        
        runningPanel.getInputMap().put(KeyStroke.getKeyStroke(" "), "requestDrop");
        runningPanel.getActionMap().put("requestDrop", new Action() {

            public void addPropertyChangeListener(PropertyChangeListener listener) {
                // TODO Auto-generated method stub
                
            }

            public Object getValue(String key) {
                // TODO Auto-generated method stub
                return null;
            }

            public boolean isEnabled() {
                // TODO Auto-generated method stub
                return false;
            }

            public void putValue(String key, Object value) {
                // TODO Auto-generated method stub
                
            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {
                // TODO Auto-generated method stub
                
            }

            public void setEnabled(boolean b) {
                // TODO Auto-generated method stub
                
            }

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        
        getContentPane().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed");
            }
            
            public void keyReleased(KeyEvent e) {
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
    
    public boolean isFocusOwner() {
        return true;
    }
}

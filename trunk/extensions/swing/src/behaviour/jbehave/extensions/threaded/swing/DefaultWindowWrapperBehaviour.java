package jbehave.extensions.threaded.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;
import jbehave.extensions.threaded.time.TimeoutException;

public class DefaultWindowWrapperBehaviour extends UsingMiniMock {
	
	public void shouldClickAButtonOnAWindow() throws Exception {
		
		JFrame frame = new JFrame();
		frame.setName("a.window");
		
		JButton button = new JButton("Press Me!");
		button.setName("a.button");
		
		Mock actionListener = mock(ActionListener.class);
		actionListener.expects("actionPerformed");
		
		button.addActionListener((ActionListener)actionListener);
		
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
		frame.getContentPane().add(button);
		frame.pack();
		frame.setVisible(true);
		
		wrapper.clickButton("a.button");
		
		frame.dispose();
		
		verifyMocks();
	}
	
	public void shouldEnterTextIntoTextComponents() throws Exception {
		
		JFrame frame = new JFrame();
		frame.setName("a.window");
		
		JTextComponent textField = new JTextField();
		textField.setName("a.textfield");
		
		JTextComponent textArea = new JTextArea();
		textArea.setName("b.textarea");
		
		frame.getContentPane().setLayout(new FlowLayout());
		
		frame.getContentPane().add(textField);
		frame.getContentPane().add(textArea);
		frame.pack();
		
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
		frame.setVisible(true);
		wrapper.enterText("a.textfield", "Text1");
		wrapper.enterText("b.textarea", "Text2");
		
		ensureThat(textField.getText(), eq("Text1"));
		ensureThat(textArea.getText(), eq("Text2"));
		
		frame.dispose();
	}
	
	public void shouldFindComponent() throws ComponentFinderException, TimeoutException  {
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
		JFrame frame = new JFrame();
		frame.setName("a.window");
		
		JPanel panel = new JPanel();
		panel.setName("a.panel");
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
		ensureThat(wrapper.findComponent("a.panel"), eq(panel));
		
		frame.dispose();
	}
    
    public void shouldCloseWindows() throws TimeoutException {
        DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
        
        JFrame frame = new JFrame();
        frame.setName("a.window");
        frame.setVisible(true);
        
        wrapper.closeWindow();
        
        ensureThat(!frame.isShowing());
    }
    
    public void shouldSimulateKeyPresses() throws TimeoutException {
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
        JFrame frame = new JFrame();
        frame.setName("a.window");
        frame.setVisible(true);
        
        Constraint spaceKeyEvent = new Constraint() {
			public boolean matches(Object arg) {
				return ((KeyEvent)arg).getKeyCode() == KeyEvent.VK_SPACE;
			}
        };
        
        Mock keyListener = mock(KeyListener.class);
        keyListener.stubs("keyTyped");
        keyListener.stubs("keyPressed");
        keyListener.expects("keyReleased").once().with(spaceKeyEvent);
        
        frame.addKeyListener((KeyListener) keyListener);
        
        wrapper.pressKey(KeyEvent.VK_SPACE);
    }
}

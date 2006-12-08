package org.jbehave.threaded.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.Mock;
import org.jbehave.threaded.time.TimeoutException;


public class DefaultWindowWrapperBehaviour extends UsingMiniMock {
	
	public void shouldClickAButtonOnAWindow() throws Exception {
		checkForHeadless();
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
        
		try {
            JFrame frame = disposeOnCloseFrame();
    		frame.setName("a.window");
    		
    		JButton button = new JButton("Press Me!");
    		button.setName("a.button");
    		
    		Mock actionListener = mock(ActionListener.class);
    		actionListener.expects("actionPerformed");
    		
    		button.addActionListener((ActionListener)actionListener);
    		
    		
    		frame.getContentPane().add(button);
    		frame.pack();
    		frame.setVisible(true);
    		
    		wrapper.clickButton("a.button");
    		
    		verifyMocks();
        } finally {
            wrapper.closeWindow();
        }
	}

	
	public void shouldEnterTextIntoTextComponents() throws Exception {
        todo("not working on linux");
        checkForHeadless();
        DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");

        try {
            JFrame frame = disposeOnCloseFrame();
    		frame.setName("a.window");
    		
    		JTextComponent textField = new JTextField();
    		textField.setName("a.textfield");
    		
    		JTextComponent textArea = new JTextArea();
    		textArea.setName("b.textarea");
    		
    		frame.getContentPane().setLayout(new FlowLayout());
    		
    		frame.getContentPane().add(textField);
    		frame.getContentPane().add(textArea);
    		frame.pack();
    		
    		
    		frame.setVisible(true);
    		wrapper.enterText("a.textfield", "Text1");
    		wrapper.enterText("b.textarea", "Text2");
    		
    		ensureThat(textField.getText(), eq("Text1"));
    		ensureThat(textArea.getText(), eq("Text2"));
        } finally {
            wrapper.closeWindow();
        }
	}
	
	public void shouldFindComponent() throws ComponentFinderException, TimeoutException  {
	    checkForHeadless();
	    DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
        try {
    		
    		JFrame frame = disposeOnCloseFrame();
    		frame.setName("a.window");
    		
    		JPanel panel = new JPanel();
    		panel.setName("a.panel");
    		
    		frame.getContentPane().add(panel);
    		frame.setVisible(true);
    		
    		ensureThat(wrapper.findComponent("a.panel"), eq(panel));
        } finally {
            wrapper.closeWindow();
        }
	}
    
    public void shouldCloseWindows() throws TimeoutException {
        checkForHeadless();
        DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
        
        JFrame frame = disposeOnCloseFrame();
        frame.setName("a.window");
        frame.setVisible(true);
        
        wrapper.closeWindow();
        ensureThat(!frame.isShowing());
        frame.dispose();
    }


    private JFrame disposeOnCloseFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }
    
    public void shouldSimulateKeyPressesForInputMap() throws TimeoutException {
        todo("not working on linux");
        checkForHeadless();
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
        try {
            AFrame frame = new AFrame();            

            Mock action = mock(Action.class);
            action.stubs("isEnabled").will(returnValue(true));
            action.expects("actionPerformed").with(anything());
            
            frame.contentPanel.getActionMap().put("an action", (Action) action);
            
            wrapper.pressKeychar(' ');
            
            verifyMocks();
        } finally {
            wrapper.closeWindow();
        }
    }
    
    public void shouldSimulateKeyPressesForKeyListeners() throws TimeoutException {
        checkForHeadless();
        DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
        
        try {
            JFrame frame = disposeOnCloseFrame();
            JPanel panel = new JPanel();
            frame.setContentPane(panel);
            frame.setName("a.window");
            frame.pack();
            frame.setVisible(true);
            
            Matcher spaceKeyEvent = new Matcher() {
                public boolean matches(Object arg) {
                    return ((KeyEvent)arg).getKeyCode() == KeyEvent.VK_SPACE ||
                        ((KeyEvent)arg).getKeyChar() == ' ';
                }
            };
            Mock keyListener = mock(KeyListener.class);
            keyListener.stubs("keyTyped").once().with(spaceKeyEvent);
            keyListener.stubs("keyPressed").once().with(spaceKeyEvent);
            keyListener.expects("keyReleased").once().with(spaceKeyEvent);
            frame.addKeyListener((KeyListener) keyListener);    
            
            wrapper.pressKeychar(' ');
            
            verifyMocks();
        } finally {
            wrapper.closeWindow();
        }
    }
    

    private void checkForHeadless() {
        new HeadlessChecker().check();
    }

    public class AFrame extends JFrame {
        private JPanel contentPanel = new JPanel();
        public AFrame() {
            setName("a.window");
            setContentPane(contentPanel);

            contentPanel.getInputMap().put(KeyStroke.getKeyStroke(' '), "an action");
            
            this.pack();
            this.setVisible(true);
            
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}

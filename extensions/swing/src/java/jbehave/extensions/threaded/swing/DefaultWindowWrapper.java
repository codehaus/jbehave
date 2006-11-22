package jbehave.extensions.threaded.swing;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.text.JTextComponent;

import jbehave.extensions.threaded.WindowGrabber;
import jbehave.extensions.threaded.time.TimeoutException;

public class DefaultWindowWrapper implements WindowWrapper {

	// Use of the DefaultWindowWrapper starts it grabbing windows ASAP.
	private static final WindowGrabber grabber = new WindowGrabber();
	
	private Window window;
	private String windowName;
	private ComponentFinder finder;
	private EventQueue sysQueue;

	private Idler idler;
		
	public DefaultWindowWrapper(String windowName) {
		this(windowName, new ComponentFinder());
	}
	
	public DefaultWindowWrapper(String windowName, ComponentFinder finder) {
		this.windowName = windowName;
		this.finder = finder;
		sysQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		idler = new Idler();
	}

	public void closeWindow() throws TimeoutException {
		getWindow().dispose();
		idler.waitForIdle();
	}
	
	public void clickButton(String componentName) throws ComponentFinderException, TimeoutException {
		AbstractButton button = (AbstractButton) finder.findExactComponent(
				getWindow(), new NamedComponentFilter(componentName));
		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_PRESSED));
		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_RELEASED));
		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_CLICKED));
		idler.waitForIdle();
	}

	public void enterText(String componentName, String text) throws ComponentFinderException, TimeoutException {
		Component component = findComponent(componentName);
		
		component.requestFocus();
		
		if (component instanceof TextComponent) {
			typeIntoTextComponent((TextComponent)component, text);
		} else if (component instanceof JTextComponent) {
			typeIntoJTextComponent((JTextComponent)component, text);
		}		
		idler.waitForIdle();
	}
		

	public void typeIntoTextComponent(TextComponent textComponent, String text) {
		for (int i = 0; i < text.length(); i++) {
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_PRESSED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_RELEASED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_TYPED));
		}
		idler.waitForIdle();
	}
	
	public void pressKey(int keycode) throws TimeoutException {
        try {
            Robot robot = new Robot();
            robot.keyPress(keycode);
            robot.keyRelease(keycode);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        
		idler.waitForIdle();
	}

	
	public void typeIntoJTextComponent(JTextComponent textComponent, String text) {
		for (int i = 0; i < text.length(); i++) {
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_PRESSED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_RELEASED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_TYPED));
		}
		idler.waitForIdle();
	}	

	public Component findComponent(String componentName) throws ComponentFinderException, TimeoutException {
		return finder.findExactComponent(getWindow(), new NamedComponentFilter(componentName));
	}
	
	private Window getWindow() throws TimeoutException {
		if (window == null) {
			idler.waitForIdle();
			window = grabber.getWindow(windowName);			
		}
		return window;
	}


	private MouseEvent createMouseEvent(AbstractButton button, int id) {
		return new MouseEvent(button, 
				id, 
				System.currentTimeMillis(), 
				MouseEvent.BUTTON1_MASK,
				button.getWidth() / 2,
				button.getHeight() / 2,
				1,
				false);
	}
	
	private AWTEvent createKeyPressEvent(Component component, char c, int id) {
		return new KeyEvent(component, 
				id, 
				System.currentTimeMillis(),
				0,
				KeyEvent.VK_UNDEFINED,
				c);
	}
}

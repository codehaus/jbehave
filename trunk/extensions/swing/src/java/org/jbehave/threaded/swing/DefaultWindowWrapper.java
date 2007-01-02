package org.jbehave.threaded.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.text.JTextComponent;

import org.jbehave.threaded.WindowGrabber;
import org.jbehave.threaded.time.TimeoutException;


public class DefaultWindowWrapper implements WindowWrapper {

	// Use of the DefaultWindowWrapper starts it grabbing windows ASAP.
	private static final WindowGrabber grabber = new WindowGrabber();
	
	private final String windowName;
	private final ComponentFinder finder;
	private final EventQueue sysQueue;
	private Window window;

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
        button.doClick(200);
//		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_PRESSED));
//		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_RELEASED));
//		sysQueue.postEvent(createMouseEvent(button, MouseEvent.MOUSE_CLICKED));
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
	}
		

	public void typeIntoTextComponent(TextComponent textComponent, String text) {
		for (int i = 0; i < text.length(); i++) {
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_PRESSED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_RELEASED));
			sysQueue.postEvent(createKeyPressEvent(textComponent, text.charAt(i), KeyEvent.KEY_TYPED));
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
	
    /**
     * Use this for any key which doesn't have a corresponding character.
     */
	public void pressKeycode(int keycode) throws TimeoutException {
        //NB: Don't use the Robot.
        //Why not the Robot?
        //Because the Robot tries to press the space bar, which is really annoying if you're running things
        //from Eclipse's vm.

        sysQueue.postEvent(createKeyPressEvent(getWindow(), keycode, KeyEvent.KEY_PRESSED));
        sysQueue.postEvent(createKeyPressEvent(getWindow(), keycode, KeyEvent.KEY_RELEASED));
            
		idler.waitForIdle();
	}
    
    public void pressKeychar(char key) throws TimeoutException {
        sysQueue.postEvent(createKeyPressEvent(getWindow(), key, KeyEvent.KEY_PRESSED));
        sysQueue.postEvent(createKeyPressEvent(getWindow(), key, KeyEvent.KEY_RELEASED));
        sysQueue.postEvent(createKeyPressEvent(getWindow(), key, KeyEvent.KEY_TYPED));
            
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
	
	private AWTEvent createKeyPressEvent(Component component, char c, int id) {
		return new KeyEvent(component, 
				id, 
				System.currentTimeMillis(),
				0,
				KeyEvent.VK_UNDEFINED,
				c);
	}
    
    private AWTEvent createKeyPressEvent(Component component, int keycode, int id) {
        return new KeyEvent(component, 
                id, 
                System.currentTimeMillis(),
                0,
                keycode,
                KeyEvent.CHAR_UNDEFINED);
    }

    public void requestWindowFocus() throws TimeoutException {
        getWindow().requestFocus();
        idler.waitForIdle();
    }
}

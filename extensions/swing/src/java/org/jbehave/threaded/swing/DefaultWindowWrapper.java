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

import org.jbehave.core.threaded.TimeoutException;


public class DefaultWindowWrapper implements WindowWrapper {

	// Use of the DefaultWindowWrapper starts it grabbing windows ASAP.
	private static final WindowGrabber grabber = new WindowGrabber();
    
	private final CharacterTyper typer;
    private final ButtonClicker clicker;
	
	private final String windowName;
	private final ComponentFinder finder;
	private final EventQueue sysQueue;
	private Window window;

	private Idler idler;

    private Focuser focuser;

		
	public DefaultWindowWrapper(String windowName) {
		this(windowName, new ComponentFinder());
	}
	
	public DefaultWindowWrapper(String windowName, ComponentFinder finder) {
		new HeadlessChecker().check();
		this.windowName = windowName;
		this.finder = finder;
		sysQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		idler = new Idler();
        typer = new CharacterTyper();
        clicker = new ButtonClicker();
        focuser = new Focuser();
	}

	public void closeWindow() throws TimeoutException {
		getOpenWindow().dispose();
		idler.waitForIdle();
	}
	
	public void clickButton(String componentName) throws ComponentFinderException, TimeoutException {
		AbstractButton button = (AbstractButton) finder.findExactComponent(
				getOpenWindow(), new NamedComponentFilter(componentName));
        clicker.click(button);

	}

	public void enterText(String componentName, String text) throws ComponentFinderException, TimeoutException {
		Component component = findComponent(componentName);
		typer.typeIntoComponent(component, text);
	}
		

	public void typeIntoTextComponent(TextComponent component, String text) {
		typer.typeIntoComponent(component, text);
	}
    
    public void typeIntoJTextComponent(JTextComponent component, String text) {
    	typer.typeIntoComponent(component, text);
    }   
	
    /**
     * Use this for any key which doesn't have a corresponding character (eg: directional keys).
     * If the key has a character, listeners will not always detect this mimicry.
     */
	public void pressKeycode(int keycode) throws TimeoutException {
	    sysQueue.postEvent(createKeyPressEvent(getOpenWindow(), keycode, KeyEvent.KEY_PRESSED));
	    sysQueue.postEvent(createKeyPressEvent(getOpenWindow(), keycode, KeyEvent.KEY_RELEASED));
            
		idler.waitForIdle();
	}

    /**
     * Use this for any key which has a valid character associated with it, when it is being pressed
     * (eg: as a game control key) rather than being typed into a text component.
     */
    public void pressKeychar(char key) throws TimeoutException {
        typer.pressKeychar(getOpenWindow(), key);
    }
    
	public Component findComponent(String componentName) throws ComponentFinderException, TimeoutException {
		return finder.findExactComponent(getOpenWindow(), new NamedComponentFilter(componentName));
	}
	
	public Window getOpenWindow() throws TimeoutException {
		if (window == null) {
			idler.waitForIdle();
			window = grabber.getWindow(windowName);
		}
		return window;
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
        focuser.requestFocusOn(getOpenWindow());
        idler.waitForIdle();
    }
    

}

package org.jbehave.threaded.swing;

import java.awt.Component;
import java.awt.TextComponent;
import java.awt.Window;

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
		typer.pressKeycode(getOpenWindow(), keycode);
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
	



    public void requestWindowFocus() throws TimeoutException {
        focuser.requestFocusOn(getOpenWindow());
        idler.waitForIdle();
    }
    

}

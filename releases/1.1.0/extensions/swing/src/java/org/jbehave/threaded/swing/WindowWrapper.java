package org.jbehave.threaded.swing;

import java.awt.Component;

import org.jbehave.core.threaded.TimeoutException;



public interface WindowWrapper {
	
	public void clickButton(String name) throws ComponentFinderException, TimeoutException;	

	public void enterText(String componentName, String text) throws ComponentFinderException, TimeoutException;

	public Component findComponent(String componentName) throws ComponentFinderException, TimeoutException;
	
	public void closeWindow() throws TimeoutException;

	public void pressKeycode(int keycode) throws TimeoutException;
    
    public void pressKeychar(char keychar) throws TimeoutException;
}

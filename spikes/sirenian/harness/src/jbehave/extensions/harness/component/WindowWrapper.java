package jbehave.extensions.harness.component;

import java.awt.Component;

import jbehave.extensions.harness.time.TimeoutException;


public interface WindowWrapper {
	
	public void clickButton(String name) throws ComponentFinderException, TimeoutException;	

	public void enterText(String componentName, String text) throws ComponentFinderException, TimeoutException;

	public Component findComponent(String componentName) throws ComponentFinderException, TimeoutException;
	
	public void closeWindow() throws TimeoutException;
}

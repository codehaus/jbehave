package com.thoughtworks.jbehave.extensions.harness.component;

import javax.swing.JButton;

import com.thoughtworks.jbehave.core.Verify;

public class NamedComponentFilterBehaviour {

	public void shouldMatchComponentsWithGivenName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter("frodoButton");
		Verify.that(filter.matches(buttonFrodo));	
	}
	
	public void shouldNotMatchComponentsWithWrongName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter("gandalfButton");
		Verify.that(!filter.matches(buttonFrodo));		
	}
	
	public void shouldMatchIfNullComponentsWithNullName() {
		JButton button = new JButton();

		NamedComponentFilter filter = new NamedComponentFilter(null);
		Verify.that(filter.matches(button));		
	}
	
	public void shouldNotMatchIfNullComponentsWithNonNullName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter(null);
		Verify.that(!filter.matches(buttonFrodo));
	}
}

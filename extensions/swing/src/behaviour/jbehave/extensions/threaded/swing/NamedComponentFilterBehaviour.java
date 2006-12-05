package jbehave.extensions.threaded.swing;

import javax.swing.JButton;

import jbehave.core.mock.UsingMatchers;
import jbehave.extensions.threaded.swing.NamedComponentFilter;


public class NamedComponentFilterBehaviour extends UsingMatchers {

	public void shouldMatchComponentsWithGivenName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter("frodoButton");
		ensureThat(filter.matches(buttonFrodo));	
	}
	
	public void shouldNotMatchComponentsWithWrongName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter("gandalfButton");
		ensureThat(!filter.matches(buttonFrodo));		
	}
	
	public void shouldMatchIfNullComponentsWithNullName() {
		JButton button = new JButton();

		NamedComponentFilter filter = new NamedComponentFilter(null);
		ensureThat(filter.matches(button));		
	}
	
	public void shouldNotMatchIfNullComponentsWithNonNullName() {
		JButton buttonFrodo = new JButton("Frodo");
		buttonFrodo.setName("frodoButton");

		NamedComponentFilter filter = new NamedComponentFilter(null);
		ensureThat(!filter.matches(buttonFrodo));
	}
}

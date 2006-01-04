package jbehave.extensions.harness.component;

import javax.swing.JButton;

import jbehave.core.minimock.UsingConstraints;
import jbehave.extensions.harness.component.NamedComponentFilter;


public class NamedComponentFilterBehaviour extends UsingConstraints {

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

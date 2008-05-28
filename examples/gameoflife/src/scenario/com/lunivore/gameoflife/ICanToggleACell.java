package com.lunivore.gameoflife;

import org.jbehave.scenario.Scenario;

import com.lunivore.gameoflife.steps.GridSteps;

public class ICanToggleACell extends Scenario {
	
	@SuppressWarnings("unchecked")
	public ICanToggleACell() {
		super(new GridSteps());
	}
}

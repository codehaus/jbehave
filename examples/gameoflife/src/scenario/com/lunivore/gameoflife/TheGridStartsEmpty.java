package com.lunivore.gameoflife;

import org.jbehave.scenario.Scenario;

import com.lunivore.gameoflife.steps.GridSteps;

public class TheGridStartsEmpty extends Scenario {
	
	@SuppressWarnings("unchecked")
	public TheGridStartsEmpty() {
		super(new GridSteps());
	}
}

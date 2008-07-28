package com.lunivore.noughtsandcrosses;

import org.jbehave.scenario.Scenario;

import com.lunivore.noughtsandcrosses.steps.GridSteps;


public class TheGridStartsEmpty extends Scenario {
	public TheGridStartsEmpty() {
		super(new GridSteps());
	}
}

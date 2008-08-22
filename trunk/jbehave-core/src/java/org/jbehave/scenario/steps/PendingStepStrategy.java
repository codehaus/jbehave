package org.jbehave.scenario.steps;

import org.jbehave.scenario.errors.ErrorStrategy;

public interface PendingStepStrategy extends ErrorStrategy {

	PendingStepStrategy PASSING = new PendingStepStrategy() { 
		public void handleError(Throwable throwable) {}
	};
	
	PendingStepStrategy FAILING = new PendingStepStrategy() {
		public void handleError(Throwable throwable) throws Throwable {
			throw throwable;
		} 
	};
}

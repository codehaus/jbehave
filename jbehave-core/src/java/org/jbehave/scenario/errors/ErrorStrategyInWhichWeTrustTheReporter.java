package org.jbehave.scenario.errors;

import static org.junit.Assert.fail;

public class ErrorStrategyInWhichWeTrustTheReporter implements ErrorStrategy {
	public void handleError(Throwable throwable) throws Throwable {
		fail("An error occurred while running the scenarios; please check output for details.");
	}
}

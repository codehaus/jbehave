package org.jbehave.scenario.errors;

/**
 * PendingErrorStrategy allows to define how pending error are handled. Two
 * standard strategies are provided:
 * <ul>
 * <li>{@link PASSING}: passes scenarios upon pending errors</li>
 * <li>{@link FAILING}: fails scenario upon pending errors</li>
 * </ul>
 */
public interface PendingErrorStrategy extends ErrorStrategy {

	/**
	 * Strategy that passes scenarios upon pending errors
	 */
	PendingErrorStrategy PASSING = new PendingErrorStrategy() {
		public void handleError(Throwable throwable) {
		}
	};

	/**
	 * Strategy that fails scenario upon pending errors
	 */
	PendingErrorStrategy FAILING = new PendingErrorStrategy() {
		public void handleError(Throwable throwable) throws Throwable {
			throw throwable;
		}
	};
}

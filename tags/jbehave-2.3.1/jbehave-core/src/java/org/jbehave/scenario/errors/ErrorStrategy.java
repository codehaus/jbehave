package org.jbehave.scenario.errors;

/**
 * ErrorStrategy allows to define error handling strategies. Two standard
 * strategies are provided:
 * <ul>
 * <li>{@link SILENT}: silently absorbs the error</li>
 * <li>{@link RETHROW}: rethrows the error</li>
 * </ul>
 */
public interface ErrorStrategy {

	/**
	 * Strategy that silently absorbs the error
	 */
	ErrorStrategy SILENT = new ErrorStrategy() {
		public void handleError(Throwable throwable) {
		}
	};
	
	/**
	 * Strategy that rethrows the error
	 */
	ErrorStrategy RETHROW = new ErrorStrategy() {
		public void handleError(Throwable throwable) throws Throwable {
			throw throwable;
		}
	};

	void handleError(Throwable throwable) throws Throwable;

}

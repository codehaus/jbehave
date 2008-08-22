package org.jbehave.scenario.errors;

public interface ErrorStrategy {

	ErrorStrategy SILENT = new ErrorStrategy(){ public void handleError(Throwable throwable) {}};
	ErrorStrategy RETHROW = new ErrorStrategy() {
		public void handleError(Throwable throwable) throws Throwable {
			throw throwable;
		}};
		
	void handleError(Throwable throwable) throws Throwable;

}

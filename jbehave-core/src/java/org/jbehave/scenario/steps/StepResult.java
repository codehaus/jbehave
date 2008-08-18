package org.jbehave.scenario.steps;

import org.jbehave.scenario.ScenarioReporter;


public abstract class StepResult {

	public static class Failed extends StepResult {

		public Failed(String step, Throwable throwable) {
			super(step, throwable);
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.failed(step, throwable);
		}
	}

	public static class NotPerformed extends StepResult {

		public NotPerformed(String step) {
			super(step);
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.notPerformed(step);
		}
	}


	public static class Pending extends StepResult {
		public Pending(String step) {
			this(step, new PendingError("Pending: " + step));
		}

		public Pending(String stepAsString, PendingError e) {
			super(stepAsString, e);
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.pending(step);
		}
	}

	public static class Success extends StepResult {
		public Success(String string) {
			super(string);
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.successful(step);
		}
	}

	protected final String step;
	protected final Throwable throwable;

	public StepResult(String step) {
		this(step, null);
	}

	public StepResult(String step, Throwable throwable) {
		this.step = step;
		this.throwable = throwable;
	}

	public static StepResult success(String step) {
		return new Success(step);
	}

	public static StepResult pending(String step) {
		return new Pending(step);
	}
	
	public static StepResult pending(String stepAsString, PendingError e) {
		return new Pending(stepAsString, e);
	}

	public static StepResult notPerformed(String step) {
		return new NotPerformed(step);
	}

	public static StepResult failure(String step, Throwable e) {
		return new Failed(step, e);
	}

	public abstract void describeTo(ScenarioReporter reporter);
	

	public Throwable getThrowable() {
		return throwable;
	}
}

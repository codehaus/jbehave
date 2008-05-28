package org.jbehave.scenario.steps;

import org.jbehave.scenario.ScenarioReporter;


public abstract class StepResult {

	public static class Failed extends StepResult {

		private final Throwable e;

		public Failed(String step, Throwable e) {
			super(step);
			this.e = e;
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.failed(step, e);
		}

		@Override
		public boolean shouldContinue() {
			return false;
		}
		
		@Override
		public Throwable getThrowable() {
			return e;
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

		
		@Override
		public boolean shouldContinue() {
			throw new UnsupportedOperationException(
					"I have no idea whether 'notPerformed' results should continue. ScenarioRunners " +
					"should not need to check whether to continue if they've already decided not to continue.");
		}

	}


	public static class Pending extends StepResult {

		public Pending(String step) {
			super(step);
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
			reporter.pending(step);
		}

		@Override
		public boolean shouldContinue() {
			return false;
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

		@Override
		public boolean shouldContinue() {
			return true;
		}
	}

	protected final String step;

	public StepResult(String step) {
		this.step = step;
	}

	public static StepResult success(String step) {
		return new Success(step);
	}

	public static StepResult pending(String step) {
		return new Pending(step);
	}
	

	public static StepResult notPerformed(String step) {
		return new NotPerformed(step);
	}

	public static StepResult failure(String step, Throwable e) {
		return new Failed(step, e);
	}

	public abstract void describeTo(ScenarioReporter reporter);
	
	public abstract boolean shouldContinue();

	public Throwable getThrowable() {
		return null;
	}
}

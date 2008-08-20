package org.jbehave.scenario.reporters;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.ScenarioReporter;

/**
 * Swallows the reports from all scenarios that pass, providing output
 * only for failing or pending scenarios.
 */
public class PassSilentlyDecorator implements ScenarioReporter {



	private final ScenarioReporter delegate;
	private List<Todo> currentScenario;
	private State state = State.SILENT;

	public PassSilentlyDecorator(ScenarioReporter delegate) {
		this.delegate = delegate;
	}

	public void failed(final String step, final Throwable e) {
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.failed(step, e);
			}
		});
		setStateToNoisy();
	}

	public void notPerformed(final String step) {
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.notPerformed(step);
			}
		});
	}

	public void pending(final String step) {
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.pending(step);
			}
		});
		setStateToNoisy();
	}

	private void setStateToNoisy() {
		state = new State(){
			public void report() {
				for (Todo todo : currentScenario) {
					todo.doNow();
				}
			}};
	}

	public void successful(final String step) {
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.successful(step);
			}
		});
	}

	public void afterScenario() {
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.afterScenario();
			}
		});
		state.report();
		state = State.SILENT;
	}

	public void beforeScenario(final String title) {
		currentScenario = new ArrayList<Todo>();
		currentScenario.add(new Todo() {
			public void doNow() {
				delegate.beforeScenario(title);
			}
		});
	}


	private static interface Todo {
		void doNow();
	}
	

	private interface State {
		State SILENT = new State(){public void report() {}};
		
		void report();
	};
}

package org.jbehave.scenario;

public interface ScenarioReporter {

	void successful(String step);

	void pending(String step);

	void notPerformed(String step);

	void failed(String step, Throwable e);
}

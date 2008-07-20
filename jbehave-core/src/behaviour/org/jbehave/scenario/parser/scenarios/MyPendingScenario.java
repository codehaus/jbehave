package org.jbehave.scenario.parser.scenarios;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.ScenarioFileLoader;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;

public class MyPendingScenario extends Scenario {

	public MyPendingScenario() {
		// Making sure this doesn't output to the build while it's running
		super(new ScenarioFileLoader(), new PrintStreamScenarioReporter(new PrintStream(new ByteArrayOutputStream())));
	}
}
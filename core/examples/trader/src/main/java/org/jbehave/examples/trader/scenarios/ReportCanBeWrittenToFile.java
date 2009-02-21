package org.jbehave.examples.trader.scenarios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;

public class ReportCanBeWrittenToFile extends JUnitScenario {

	public ReportCanBeWrittenToFile() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public ReportCanBeWrittenToFile(final ClassLoader classLoader) {
		super(new MyConfiguration(classLoader), new TraderSteps(classLoader));
	}

	private static class MyConfiguration extends MostUsefulConfiguration {
		private final ClassLoader classLoader;
		private final OutputStream outputStream;

		public MyConfiguration(ClassLoader classLoader) {
			this.classLoader = classLoader;
			try {
				File file = File.createTempFile("ScenarioReport", ".txt");
				this.outputStream = new FileOutputStream(file);
				System.out.println("Writing output to "+file.getAbsolutePath());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public ScenarioDefiner forDefiningScenarios() {
			return new ClasspathScenarioDefiner(
					new UnderscoredCamelCaseResolver(".scenario"),
					new PatternScenarioParser(new PropertyBasedConfiguration()),
					classLoader);
		}

		public ScenarioReporter forReportingScenarios() {
			return new PrintStreamScenarioReporter(
					new PrintStream(outputStream));
		}
	}

}

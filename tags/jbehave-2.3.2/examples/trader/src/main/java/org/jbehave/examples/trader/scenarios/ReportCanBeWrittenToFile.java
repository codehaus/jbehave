package org.jbehave.examples.trader.scenarios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.jbehave.examples.trader.TraderSteps;
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
        super(new FileOutputConfiguration(), new TraderSteps());
    }

    private static class FileOutputConfiguration extends MostUsefulConfiguration {
        private final OutputStream outputStream;

        public FileOutputConfiguration() {
            try {
                File file = File.createTempFile("ScenarioReport", ".txt");
                this.outputStream = new FileOutputStream(file);
                System.out.println("Writing output to " + file.getAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public ScenarioDefiner forDefiningScenarios() {
            return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(".scenario"),
                    new PatternScenarioParser(new PropertyBasedConfiguration()));
        }

        public ScenarioReporter forReportingScenarios() {
            return new PrintStreamScenarioReporter(new PrintStream(outputStream));
        }
    }

}

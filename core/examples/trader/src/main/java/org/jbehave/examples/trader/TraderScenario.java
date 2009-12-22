package org.jbehave.examples.trader;

import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioNameResolver;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.StatisticsScenarioReporter;
import org.jbehave.scenario.reporters.DelegatingScenarioReporter;
import org.jbehave.scenario.reporters.FilePrintStreamFactory;
import org.jbehave.scenario.reporters.HtmlPrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.reporters.FilePrintStreamFactory.FileConfiguration;

public class TraderScenario extends JUnitScenario {

    private static ScenarioNameResolver converter = new UnderscoredCamelCaseResolver(".scenario");

    public TraderScenario(final Class<? extends RunnableScenario> scenarioClass) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(converter, new PatternScenarioParser(this));
            }

            @Override
            public ScenarioReporter forReportingScenarios() {
                return new DelegatingScenarioReporter(
                        // report to System.out
                        new PrintStreamScenarioReporter(),
                        // report to .txt file in PLAIN format
                        new PrintStreamScenarioReporter(new FilePrintStreamFactory(scenarioClass, converter,
                                new FileConfiguration("txt")).getPrintStream()),
                        // report to .html file in HTML format
                        new HtmlPrintStreamScenarioReporter(new FilePrintStreamFactory(scenarioClass, converter,
                                new FileConfiguration("html")).getPrintStream()),
                        // report to .stats file in Properties format
                        new StatisticsScenarioReporter(new FilePrintStreamFactory(scenarioClass, converter,
                                new FileConfiguration("stats")).getPrintStream()));

            }

        }, new TraderSteps());
    }

}
